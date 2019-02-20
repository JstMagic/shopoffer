package com.monyesom.shopoffer.Tasks;

import com.monyesom.shopoffer.entity.Offer;
import com.monyesom.shopoffer.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    OfferService offerService;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        List<Offer> validOffers = offerService.findAllValid();
        validOffers.forEach( offer ->{
            if(offer !=null){
                LocalDateTime expireDateTime = offer.getExpiringDate();
                if (expireDateTime.compareTo(LocalDateTime.now()) < 0){
                    offer.setValid(false);
                    log.info(offer.getOfferName() + " has just expired and no longer valid.");
                }
            }
        });

        offerService.upsertOffers(validOffers);
    }
}
