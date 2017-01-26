package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.order.domain.FulfillmentGroup;

public interface CandidateFulfillmentGroupOffer {

    long getId();

    int getDiscountedPrice();

    FulfillmentGroup getFulfillmentGroup();

    Offer getOffer();


    CandidateFulfillmentGroupOffer setId(int id);

    CandidateFulfillmentGroupOffer setDiscountedPrice(int discountedPrice);

    CandidateFulfillmentGroupOffer setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    CandidateFulfillmentGroupOffer setOffer(Offer offer);
}