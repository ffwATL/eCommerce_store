package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;

import java.io.Serializable;

/**
 * This class holds adjustment records during the discount calculation
 * processing.  This and other disposable objects avoid churn on the database while the
 * offer engine determines the best offer(s) for the order being priced.
 */
public interface PromotableFulfillmentGroupAdjustment extends Serializable{

    Offer getOffer();

    /**
     * Returns the associated promotableFulfillmentGroup
     */
    PromotableFulfillmentGroup getPromotableFulfillmentGroup();

    /**
     * Returns the associated promotableCandidateOrderOffer
     */
    PromotableCandidateFulfillmentGroupOffer getPromotableCandidateFulfillmentGroupOffer();

    /**
     * Returns the value of this adjustment
     */
    int getSaleAdjustmentValue();

    /**
     * Returns the value of this adjustment
     */
    int getRetailAdjustmentValue();

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

    /**
     * Updates the adjustmentValue to the sales or retail value based on the passed in param
     */
    void finalizeAdjustment(boolean useSaleAdjustments);

    boolean isAppliedToSalePrice();
}