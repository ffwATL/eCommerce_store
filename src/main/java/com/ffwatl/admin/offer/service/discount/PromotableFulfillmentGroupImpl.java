package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PromotableFulfillmentGroupImpl implements PromotableFulfillmentGroup {

    private static final long serialVersionUID = 1L;

    private FulfillmentGroup fulfillmentGroup;
    private PromotableOrder promotableOrder;
    private PromotableItemFactory itemFactory;
    private List<PromotableOrderItem> discountableOrderItems;
    private int adjustedPrice;

    private List<PromotableFulfillmentGroupAdjustment> candidateFulfillmentGroupAdjustments = new ArrayList<>();

    public PromotableFulfillmentGroupImpl(FulfillmentGroup fulfillmentGroup,
                                          PromotableOrder promotableOrder,
                                          PromotableItemFactory itemFactory) {
        this.fulfillmentGroup = fulfillmentGroup;
        this.promotableOrder = promotableOrder;
        this.itemFactory = itemFactory;
    }



    @Override
    public void addCandidateFulfillmentGroupAdjustment(PromotableFulfillmentGroupAdjustment adjustment) {
        candidateFulfillmentGroupAdjustments.add(adjustment);
    }

    @Override
    public List<PromotableFulfillmentGroupAdjustment> getCandidateFulfillmentGroupAdjustments() {
        return candidateFulfillmentGroupAdjustments;
    }

    @Override
    public void removeAllCandidateAdjustments() {
        candidateFulfillmentGroupAdjustments.clear();
    }

    private int getSalePriceBeforeAdjustments() {
        int salePrice = fulfillmentGroup.getSaleFulfillmentPrice();
        if (salePrice == 0) {
            return fulfillmentGroup.getRetailFulfillmentPrice();
        } else {
            return salePrice;
        }
    }

    private int calculateSaleAdjustmentPrice() {
        int returnPrice = getSalePriceBeforeAdjustments();

        for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
            returnPrice = returnPrice - adjustment.getSaleAdjustmentValue();
        }
        return returnPrice;
    }

    private int calculateRetailAdjustmentPrice() {
        int returnPrice = fulfillmentGroup.getRetailFulfillmentPrice();

        for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
            returnPrice = returnPrice - adjustment.getRetailAdjustmentValue();
        }
        return returnPrice;
    }

    private void finalizeAdjustments(boolean useSaleAdjustments) {
        for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
            adjustment.finalizeAdjustment(useSaleAdjustments);
        }
    }

     /**
     * If removeUnusedAdjustments is s
     * @param useSaleAdjustments
     */
     private void removeZeroDollarAdjustments(boolean useSaleAdjustments) {
        Iterator<PromotableFulfillmentGroupAdjustment> adjustments = candidateFulfillmentGroupAdjustments.iterator();

        while (adjustments.hasNext()) {
            PromotableFulfillmentGroupAdjustment adjustment = adjustments.next();
            if (useSaleAdjustments) {
                if (adjustment.getSaleAdjustmentValue() == 0) {
                    adjustments.remove();
                }
            } else {
                if (adjustment.getRetailAdjustmentValue() == 0) {
                    adjustments.remove();
                }
            }
        }
    }

    /**
     * This method will check to see if the salePriceAdjustments or retailPriceAdjustments are better
     * and remove those that should not apply.
     */
    @Override
    public void chooseSaleOrRetailAdjustments() {
        boolean useSaleAdjustments = false;
        int saleAdjustmentPrice = calculateSaleAdjustmentPrice();
        int retailAdjustmentPrice = calculateRetailAdjustmentPrice();

        if(saleAdjustmentPrice < retailAdjustmentPrice){
            useSaleAdjustments = true;
            adjustedPrice = saleAdjustmentPrice;
        }else{
            adjustedPrice = retailAdjustmentPrice;
        }

        removeZeroDollarAdjustments(useSaleAdjustments);
        finalizeAdjustments(useSaleAdjustments);
    }

    @Override
    public FulfillmentGroup getFulfillmentGroup() {
        return fulfillmentGroup;
    }

    @Override
    public void updateRuleVariables(Map<String, Object> ruleVars) {
        ruleVars.put("fulfillmentGroup", fulfillmentGroup);
    }

    @Override
    public int calculatePriceWithAdjustments(boolean useSalePrice) {
        if (useSalePrice) {
            return calculateSaleAdjustmentPrice();
        } else {
            return calculateRetailAdjustmentPrice();
        }
    }

    @Override
    public int getFinalizedPriceWithAdjustments() {
        chooseSaleOrRetailAdjustments();
        return adjustedPrice;
    }

    @Override
    public List<PromotableOrderItem> getDiscountableOrderItems() {
        if (discountableOrderItems != null) {
            return discountableOrderItems;
        }

        discountableOrderItems = new ArrayList<>();
        List<Long> discountableOrderItemIds = new ArrayList<>();
        for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
            OrderItem orderItem = fgItem.getOrderItem();
            if (orderItem.isDiscountingAllowed()) {
                discountableOrderItemIds.add(fgItem.getOrderItem().getId());
            }
        }

        discountableOrderItems.addAll(promotableOrder
                .getDiscountableOrderItems()
                .stream()
                .filter(item -> discountableOrderItemIds.contains(item.getOrderItemId()))
                .collect(Collectors.toList()));
        return discountableOrderItems;
    }

    @Override
    public boolean canApplyOffer(PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer) {
        if (candidateFulfillmentGroupAdjustments.size() > 0) {
            if (!fulfillmentGroupOffer.getOffer().isCombinableWithOtherOffers()) {
                return false;
            }

            for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
                if (!adjustment.isCombinable()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int calculatePriceWithoutAdjustments() {
        if (fulfillmentGroup.getSaleFulfillmentPrice() != 0) {
            return fulfillmentGroup.getSaleFulfillmentPrice();
        } else {
            return fulfillmentGroup.getRetailFulfillmentPrice();
        }
    }

    @Override
    public boolean isTotalitarianOfferApplied() {
        for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
            if (adjustment.getPromotableCandidateFulfillmentGroupOffer().getOffer().isTotalitarianOffer()) {
                return true;
            }
        }
        return false;
    }
}