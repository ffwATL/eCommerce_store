package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.offer.domain.Offer;

import java.io.Serializable;

/**
 * This class holds adjustment records during the discount calculation
 * processing.  This and other disposable objects avoid churn on the database while the
 * offer engine determines the best offer(s) for the order being priced.
 */
public interface PromotableOrderItemPriceDetailAdjustment extends Serializable {

    /**
     * Returns the associated promotableOrderItemPriceDetail.
     * @return the associated promotableOrderItemPriceDetail.
     */
    PromotableOrderItemPriceDetail getPromotableOrderItemPriceDetail();

    /**
     * Returns the associated promotableCandidateItemOffer.
     * @return the associated promotableCandidateItemOffer.
     */
    Offer getOffer();

    /**
     * Returns the value of this adjustment if only retail prices can be used.
     * @return the value of this adjustment if only retail prices can be used.
     */
    int getRetailAdjustmentValue();

    /**
     * Returns the value of this adjustment if sale prices can be used.
     * @return the value of this adjustment if sale prices can be used.
     */
    int getSaleAdjustmentValue();

    /**
     * Returns the value of this adjustment.
     * @return the value of this adjustment.
     */
    int getAdjustmentValue();

    /**
     * Returns true if the value was applied to the sale price.
     * @return true if the value was applied to the sale price.
     */
    boolean isAppliedToSalePrice();

    /**
     * Returns true if this adjustment represents a combinable offer.
     */
    boolean isCombinable();

    /**
     * Returns true if this adjustment represents a totalitarian offer.
     */
    boolean isTotalitarian();

    /**
     * Returns the id of the contained offer.
     * @return the id of the contained offer.
     */
    long getOfferId();

    /**
     * Sets the adjustment price based on the passed in parameter.
     */
    void finalizeAdjustment(boolean useSalePrice);

    /**
     * Copy this adjustment.   Used when a detail that contains this adjustment needs to be split.
     * @return Copy of this adjustment
     */
    PromotableOrderItemPriceDetailAdjustment copy();
}