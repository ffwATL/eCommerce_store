package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.*;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;


public interface OfferDao {

    List<Offer> findAllOffers(FetchMode fetchMode);

    Offer findOfferById(long id, FetchMode fetchMode);

    List<Offer> findOffersByAutomaticDeliveryType(FetchMode fetchMode);

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