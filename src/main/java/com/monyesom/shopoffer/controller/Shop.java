

package com.monyesom.shopoffer.controller;

import com.monyesom.shopoffer.Exceptions.ResourceException;
import com.monyesom.shopoffer.service.OfferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import  com.monyesom.shopoffer.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class Shop {
    private final OfferService offerService;

    public Shop(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/offer")
    @ApiOperation(value = "Create a new offer")
    @ResponseBody
    public ResponseEntity<Void> createOffer(@ApiParam(name = "offer", value = " The offer to be created") @RequestBody Offer offer,
                                            UriComponentsBuilder ucBuilder) throws RuntimeException
    {
        /*
         * when trying to create a new RequirementDemand check if entry already exist based on the primaryKey
         * if it does return a bad request otherwise try to create the requirement demandDto and return created status if true
         */
        if(offer == null){
            throw new ResourceException(HttpStatus.CONFLICT, "No offer sent for creation");
        }
        Long offerId = offerService.upsertOffer(offer);
        if (offerId != null) {
            //attach path to a header
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/offer/{id}").buildAndExpand(offerId).toUri());
            return new ResponseEntity<>(headers,HttpStatus.CREATED);
        }
        else {
            throw new ResourceException(HttpStatus.NO_CONTENT, "Something went wrong and was unable to create the offer");
        }
    }

    /**
     * This function will be called from an javascript ajax as supposed to
     * the one above which is mainly just an endpoint for API calls
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/offer/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody  Offer createOffer(HttpServletRequest request) throws ParseException {
        Offer offer = new Offer();
        offer.setValid(true);
        offer.setOfferPrice(new BigDecimal(request.getParameter("offerPrice")));
        offer.setOfferName(request.getParameter("offerName"));
        offer.setProductName(request.getParameter("productName"));
        offer.setExpiringDate(LocalDateTime.parse(request.getParameter("expiringDate")));

        /*
         * when trying to create a new RequirementDemand check if entry already exist based on the primaryKey
         * if it does return a bad request otherwise try to create the requirement demandDto and return created status if true
         */
        if(offer == null){
            throw new ResourceException(HttpStatus.CONFLICT, "No offer sent for creation");
        }
        Long offerId = offerService.upsertOffer(offer);
        if (offerId != null) {
            offer.setId(offerId);
            /**
             * return the offer
             */
            return offer;
        }
        else {
            throw new ResourceException(HttpStatus.NO_CONTENT, "Something went wrong and was unable to create the offer");
        }
    }

    @PutMapping("/offer/{id}")
    @ApiOperation(value = "Update a new offer")
    @ResponseBody
    public ResponseEntity<Offer> updateOffer(@ApiParam(name = "id", value = " The offer id to apply the update") @PathVariable(value = "id") Long id,
                                             @ApiParam(name = "offer", value = " The offer to be updated")@RequestBody Offer offer,
                                             UriComponentsBuilder ucBuilder) throws RuntimeException
    {
        /*
         * when trying to create a new RequirementDemand check if entry already exist based on the primaryKey
         * if it does return a bad request otherwise try to create the requirement demandDto and return created status if true
         */
        if(id == null){
            throw new ResourceException(HttpStatus.CONFLICT, "id required");
        }
        offer.setId(id);
        Long offerId = offerService.upsertOffer(offer);
        if (offerId != null) {
            return new ResponseEntity<>(offer,HttpStatus.OK);
        }
        else {
            throw new ResourceException(HttpStatus.NO_CONTENT, "Something went wrong and was unable to create the offer");
        }
    }

    @ApiOperation(value = "Retrieve all offers in the system this will also include expired offers")
    @GetMapping("/offers")
    public ResponseEntity<List<Offer>>getAllOffers(){

        /**
         * get all available offers, this will include expired offers too
         */
        return new ResponseEntity<>(offerService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve all valid offer")
    @GetMapping("/offers/valid")
    public ResponseEntity<List<Offer>>getAllValidOffers(){

        /**
         * get all valid offers
         */
        return new ResponseEntity<>(offerService.findAllValid(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve a single offer")
    @GetMapping("/offers/{id}")
    public ResponseEntity<Offer>getAnOffer(@PathVariable(value = "id") Long offerId){
        /**
         * The offer is retried based on the offer id
         * It then checked to see if it still valid
         * if it is valid then return a status of OK
         * otherwise return NOT_FOUND with some message
         */
        Offer offer = offerService.findOne(offerId);
        /**
         * this was commented out to illustrate that expiration can also be checked by comparing both offer expiration and current time
         * rather than using the scheduler that run in the background to set offers to inValid when offers expires
         * please look in  tasks/ScheduledTasks to see the process
         */
//        if(offerService.isOfferValid(offer)){
        if(offer != null){
            if(offer.isCancelled()){
                throw new ResourceException(HttpStatus.NOT_FOUND, "The offer you've selected is no longer available");
            }else if (!offer.isValid()){
                throw new ResourceException(HttpStatus.NOT_FOUND, "The offer you've selected has now expired");
            }else{
                return new ResponseEntity<>(offer, HttpStatus.OK);
            }
        }else{
            throw new ResourceException(HttpStatus.NOT_FOUND, "The selected offer doesn't exist");
        }
    }

}
