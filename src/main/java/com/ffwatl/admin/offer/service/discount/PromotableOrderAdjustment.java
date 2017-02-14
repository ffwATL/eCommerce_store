package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.offer.domain.Offer;

import java.io.Serializable;


public interface PromotableOrderAdjustment extends Serializable{

    /**
     * Returns the associated promotableOrder
     */
    PromotableOrder getPromotableOrder();

    /**
     * Returns the associated promotableCandidateOrderOffer
     */
    Offer getOffer();

    /**
     * Returns the value of this adjustment
     */
    int getAdjustmentValue();

    /**
     * Returns true if this adjustment represents a combinable offer.
     */
    boolean isCombinable();

    /**
     * Returns true if this adjustment represents a totalitarian offer.
     */
    boolean isTotalitarian();
}