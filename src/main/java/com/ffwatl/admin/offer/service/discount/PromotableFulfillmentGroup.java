package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.order.domain.FulfillmentGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PromotableFulfillmentGroup extends Serializable {

    /**
     * Adds a fulfillmentGroupAdjustment
     */
    void addCandidateFulfillmentGroupAdjustment(PromotableFulfillmentGroupAdjustment adjustment);

    /**
     * Returns all fulfillmentGroupAdjustments;
     * @return all fulfillmentGroupAdjustments.
     */
    List<PromotableFulfillmentGroupAdjustment> getCandidateFulfillmentGroupAdjustments();

    /**
     * Removes all candidate adjustments
     */
    void removeAllCandidateAdjustments();

    /**
     * This method will check to see if the saleAdjustments or retail only adjustments are better
     * and finalize the set that achieves the best result for the customer.
     */
    void chooseSaleOrRetailAdjustments();

    /**
     * Returns the decorated FulfillmentGroup
     */
    FulfillmentGroup getFulfillmentGroup();

    /**
     * Adds the underlying fulfillmentGroup to the rule variable map.
     */
    void updateRuleVariables(Map<String, Object> ruleVars);

    /**
     * Calculates the price with adjustments.   Uses the sale or retail adjustments
     * based on the passed in parameter.
     * @param useSalePrice uses sale adjustments if true.
     */
    int calculatePriceWithAdjustments(boolean useSalePrice);

    /**
     * Calculates the price with all adjustments.   May error in the case where adjustments have
     * not been finalized with a call to chooseSaleOrRetailAdjustments.
     */
    int getFinalizedPriceWithAdjustments();

    /**
     * Return list of discountable discrete order items contained in this fulfillmentGroup.
     * @return list of discountable discrete order items contained in this fulfillmentGroup.
     */
    List<PromotableOrderItem> getDiscountableOrderItems();

    /**
     * Checks to see if the offer can be added to this fulfillmentGroup based on whether or not
     * it is combinable or if this fulfillmentGroup already has a non-combinable offer applied.
     */
    boolean canApplyOffer(PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer);

    /**
     * Returns the price of this fulfillment group if no adjustments were applied.
     * @return the price of this fulfillment group if no adjustments were applied.
     */
    int calculatePriceWithoutAdjustments();

    /**
     * Returns true if totalitarian offer was applied to this promotable fulfillment group.
     * @return true if totalitarian offer was applied to this promotable fulfillment group.
     */
    boolean isTotalitarianOfferApplied();
}
