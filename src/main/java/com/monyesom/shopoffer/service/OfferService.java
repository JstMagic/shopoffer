package com.monyesom.shopoffer.service;

import com.monyesom.shopoffer.entity.Offer;
import com.monyesom.shopoffer.repositories.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * this class allows different
 * service function to either read delete or create an Offer for a product
 */
@Service
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    /**
     * Create an offer based on the object parameter
     * if an offer is of null object then just return
     * a runtime exception
     * @param offer
     * @return offer id
     */
    public Long upsertOffer(Offer offer){
        if(offer !=null){
            LocalDateTime expireDateTime = offer.getExpiringDate();
            if(expireDateTime.compareTo(LocalDateTime.now()) > 0){
                offer.setValid(true);
            }
            offerRepo.save(offer);
            return offer.getId();
        }
        return null;
    }

    public void upsertOffers(List<Offer>offers){
        offers.forEach( offer -> {
            LocalDateTime expireDateTime =offer.getExpiringDate();
            if(expireDateTime.compareTo(LocalDateTime.now()) > 0){
                offer.setValid(true);
            }
        });
        offerRepo.saveAll(offers);
    }

    /**
     * retrieve all offer including non valid offers
     * @return
     */
    public List<Offer>findAll(){
        return offerRepo.findAll();
    }

    /**
     * retrieve only valid offers
     *
     * @return
     */
    public List<Offer> findAllValid() {
        return offerRepo.findAllByValidTrueAndCancelledFalse();
    }

    /**
     * return true or false depending on wheather offer is still valid or not
     * @param offer the offer to check for validity
     * @return a true or false
     */
    public boolean isOfferValid(Offer offer){
        boolean expired = true;

        if(offer !=null){
            LocalDateTime expireDateTime = offer.getExpiringDate();
            expired =  expireDateTime.compareTo(LocalDateTime.now()) > 0;
        }
        return expired;
    }

    /**
     * retrieve a single offer based on the offer id
     * @param offerId the offer id
     * @return an Offer object
     */
    public Offer findOne(Long offerId){
       return offerRepo.findById(offerId).orElse(null);
    }

}
