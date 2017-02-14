package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.common.rule.Rule;

import java.io.Serializable;
import java.util.List;


public interface PromotableOrderItemPriceDetail extends Serializable {

    /**
     * Adds the adjustment to the item's adjustment list and discounts the
     * item's prices by the value of the adjustment.
     * @param itemAdjustment item's adjustment
     */
    void addCandidateItemPriceDetailAdjustment(PromotableOrderItemPriceDetailAdjustment itemAdjustment);


    List<PromotableOrderItemPriceDetailAdjustment> getCandidateItemAdjustments();

    /**
     * Returns true if a notCombinableOffer or totalitarian offer was applied to this priceDetail.
     * @return true if a notCombinableOffer or totalitarian offer was applied to this priceDetail.
     */
    boolean isTotalitarianOfferApplied();

    /**
     * Returns true if a non-combinable offer has been applied to this item.
     */
    boolean isNonCombinableOfferApplied();

    /**
     * This method will check to see if the salePriceAdjustments or retailPriceAdjustments are better
     * and remove those that should not apply.
     */
    void chooseSaleOrRetailAdjustments();

    /**
     * Removes all adjustments from this detail.   Typically called when it has been determined
     * that another "totalitarian" offer has been applied.
     */
    void removeAllAdjustments();

    /**
     * Returns the promotion discounts applied to this detail object.
     * @return the promotion discounts applied to this detail object.
     */
    List<PromotionDiscount> getPromotionDiscounts();

    /**
     * Returns the times this item is being used as a promotionQualifier.
     * @return the times this item is being used as a promotionQualifier.
     */
    List<PromotionQualifier> getPromotionQualifiers();

    /**
     * Returns the quantity associated with this priceDetail.
     * @return the quantity associated with this priceDetail.
     */
    int getQuantity();

    /**
     * Sets the quantity for this price detail.
     * @param quantity the quantity for this price detail.
     */
    void setQuantity(int quantity);

    /**
     * Return the parent promotableOrderItem
     */
    PromotableOrderItem getPromotableOrderItem();

    /**
     * Returns the quantity of this item that can be used as a qualifier for the passed in itemOffer.
     * @return the quantity of this item that can be used as a qualifier for the passed in itemOffer.
     */
    int getQuantityAvailableToBeUsedAsQualifier(PromotableCandidateItemOffer itemOffer);

    /**
     * Returns the quantity of this item that can be used as a target for the passed in itemOffer;
     * @return the quantity of this item that can be used as a target for the passed in itemOffer.
     */
    int getQuantityAvailableToBeUsedAsTarget(PromotableCandidateItemOffer itemOffer);

    /**
     * Adds a promotionQualifier entry to this itemDetail.  PromotionQualifiers record the fact that this item has been
     * marked to be used as a qualifier for other items to receive a discount.
     *
     * If other conditions are met this qualifier will be finalized.
     * @return a promotionQualifier entry;
     */
    PromotionQualifier addPromotionQualifier(PromotableCandidateItemOffer itemOffer, Rule rule, int qtyToMarkAsQualifier);

    /**
     * Adds a promotionDiscount entry to this itemDetail.    PromotionDiscounts record the fact that this item has been
     * targeted to receive a discount.   If other conditions are met this discount will be finalized so that it can
     * then be set on the underlying orderItem.
     */
    void addPromotionDiscount(PromotableCandidateItemOffer itemOffer, Rule rule, int qtyToMarkAsTarget);

    /**
     * Returns the price to be used for this priceDetail taking into account whether or not the
     * sales price can be used.
     */
    int calculateItemUnitPriceWithAdjustments(boolean allowSalePrice);

    /**
     * Updates the target and qualifier quantities to indicate the number that are being used.
     */
    void finalizeQuantities();

    /**
     * Clears target and qualifier quantities that were marked for a promotion that did not have
     * enough qualifiers or targets to get applied.
     */
    void clearAllNonFinalizedQuantities();

    /**
     * Creates a key that represents a unique priceDetail
     */
    String buildDetailKey();

    /**
     * Returns the final total for this item taking into account the finalized
     * adjustments.    Intended to be called after the adjustments have been
     * finalized with a call {@link #chooseSaleOrRetailAdjustments()}.
     */
    int getFinalizedTotalWithAdjustments();

    /**
     * Returns the total adjustment value as the sum of the adjustments times the
     * quantity represented by this PriceDetail
     */
    int calculateTotalAdjustmentValue();

    /**
     * Checks to see that the discount quantities match the target quantities.
     * If not, splits this item into two.
     */
    PromotableOrderItemPriceDetail splitIfNecessary();

    /**
     * Returns true if the sale adjustments should be used.
     */
    boolean useSaleAdjustments();

    boolean isAdjustmentsFinalized();

    void setAdjustmentsFinalized(boolean adjustmentsFinalized);

    /**
     * Copies the {@link PromotableOrderItemPriceDetail} without Qualifiers, Discounts, or Adjustments
     */
    PromotableOrderItemPriceDetail shallowCopy();

    /**
     * Copies the {@link PromotableOrderItemPriceDetail} with all Finalized Qualifiers, Discounts, and Adjustments
     */
    PromotableOrderItemPriceDetail copyWithFinalizedData();
}