package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.*;

import java.util.List;


public interface OfferDao {

    List<Offer> findAllOffers();

    Offer findOfferById(long id);

    List<Offer> findOffersByAutomaticDeliveryType();

    Offer save(Offer offer);

    void delete(Offer offer);

    Offer create();

    CandidateOrderOffer createCandidateOrderOffer();

    CandidateItemOffer createCandidateItemOffer();

    CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer();

    OrderItemAdjustment createOrderItemAdjustment();

    OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment();

    OrderAdjustment createOrderAdjustment();

    FulfillmentGroupAdjustment createFulfillmentGroupAdjustment();
}