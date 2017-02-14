package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.FulfillmentGroup;

public interface FulfillmentGroupAdjustment {

    FulfillmentGroupAdjustment init(FulfillmentGroup fulfillmentGroup, Offer offer, String reason);

    long getId();

    FulfillmentGroup getFulfillmentGroup();

    Offer getOffer();

    int getAdjustmentValue();

    String getReason();

    Currency getCurrency();



    FulfillmentGroupAdjustment setId(long id);

    FulfillmentGroupAdjustment setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroupAdjustment setOffer(Offer offer);

    FulfillmentGroupAdjustment setAdjustmentValue(int adjustmentValue);

    FulfillmentGroupAdjustment setReason(String reason);

    FulfillmentGroupAdjustment setCurrency(Currency currency);

}