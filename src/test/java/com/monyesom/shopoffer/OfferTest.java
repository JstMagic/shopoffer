package com.monyesom.shopoffer;


import com.monyesom.shopoffer.entity.Offer;
import com.monyesom.shopoffer.repositories.OfferRepo;
import com.monyesom.shopoffer.service.OfferService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OfferTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepo offerRepo;

    private static Offer offer;

    private LocalDateTime expiringTimeStampTest;

    @Before
    public void setupData(){
        expiringTimeStampTest = LocalDateTime.now().minusMonths(1);
        offer = new Offer();
        offer.setId(1L);
        offer.setValid(true);
        offer.setCancelled(false);
        offer.setExpiringDate(Date.from(expiringTimeStampTest.atZone(ZoneId.systemDefault()).toInstant()));
        offer.setOfferName("Spring Sale 50% off");
        offer.setProductName("Brown Chair");
        offer.setOfferPrice(new BigDecimal(20.00));
    }

    @Test
    public void isOfferValid_not_valid(){
        boolean offerValid = offerService.isOfferValid(offer);
        Assert.assertFalse(offerValid);
    }

    @Test
    public void isOfferValid_valid(){
        expiringTimeStampTest = LocalDateTime.now().plusMonths(2);
        offer.setExpiringDate(Date.from(expiringTimeStampTest.atZone(ZoneId.systemDefault()).toInstant()));
        boolean offerValid = offerService.isOfferValid(offer);
        Assert.assertTrue(offerValid);
    }

    @Test
    public void findOne_returnValue(){
        when(offerRepo.findById(offer.getId())).thenReturn(Optional.ofNullable(offer));
        Offer offer1 = offerService.findOne(offer.getId());
        Assert.assertNotNull(offer1);
        Assert.assertEquals(offer1, offer);
    }

    @Test
    public void findOne_returnNull(){
        Offer offer1 = offerService.findOne(offer.getId());
        Assert.assertNull(offer1);
    }

    @Test
    public void findAllValid_returnValues(){
        when(offerRepo.findAllByValidTrueAndCancelledFalse()).thenReturn(Arrays.asList(offer));
        Assert.assertEquals(1, offerService.findAllValid().size());

    }
}
