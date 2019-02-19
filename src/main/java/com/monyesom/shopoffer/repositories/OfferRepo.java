package com.monyesom.shopoffer.repositories;

import com.monyesom.shopoffer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {

   List<Offer> findAllByValidTrueAndCancelledFalse();

}
