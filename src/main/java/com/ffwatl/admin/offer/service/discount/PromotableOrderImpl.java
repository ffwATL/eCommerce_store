package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.OrderAdjustment;
import com.ffwatl.admin.offer.service.OrderItemPriceComparator;
import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

import java.util.*;

public class PromotableOrderImpl implements PromotableOrder{

    private static final long serialVersionUID = 1L;

    private PromotableItemFactory itemFactory;
    private Order order;
    private List<PromotableOrderItem> allOrderItems;
    private List<PromotableOrderItem> discountableOrderItems;
    private boolean currentSortParam = false;
    private List<PromotableFulfillmentGroup> fulfillmentGroups;
    private List<PromotableOrderAdjustment> candidateOrderOfferAdjustments = new ArrayList<>();
    private boolean includeOrderAndItemAdjustments = false;
    private Map<String, Object> extraDataMap = new HashMap<>();


    public PromotableOrderImpl(Order order, PromotableItemFactory itemFactory, boolean includeOrderAndItemAdjustments) {
        this.order = order;
        this.itemFactory = itemFactory;
        this.includeOrderAndItemAdjustments = includeOrderAndItemAdjustments;

        if (includeOrderAndItemAdjustments) {
            createExistingOrderAdjustments();
        }
    }


    /**
     * Bring over the order adjustments. Intended to be used when processing
     * fulfillment orders.
     */
    private void createExistingOrderAdjustments() {
        if (order.getOrderAdjustments() != null) {
            for (OrderAdjustment adjustment : order.getOrderAdjustments()) {
                if (adjustment.getOffer() != null) {
                    PromotableCandidateOrderOffer pcoo = itemFactory.createPromotableCandidateOrderOffer(this,
                            adjustment.getOffer(), adjustment.getAdjustmentValue());
                    PromotableOrderAdjustment adj = itemFactory.createPromotableOrderAdjustment(pcoo, this,
                            adjustment.getAdjustmentValue());

                    candidateOrderOfferAdjustments.add(adj);
                }
            }
        }
    }

    @Override
    public void setOrderSubTotalToPriceWithoutAdjustments() {
        int calculatedSubTotal = calculateSubtotalWithoutAdjustments();
        order.setSubTotal(calculatedSubTotal);
    }

    @Override
    public void setOrderSubTotalToPriceWithAdjustments() {
        int calculatedSubTotal = calculateSubtotalWithAdjustments();
        order.setSubTotal(calculatedSubTotal);
    }

    private void addPromotableOrderItem(OrderItem orderItem,
                                        List<PromotableOrderItem> discountableOrderItems) {
        PromotableOrderItem item = itemFactory.createPromotableOrderItem(orderItem,
                PromotableOrderImpl.this, includeOrderAndItemAdjustments);

        discountableOrderItems.add(item);
    }

    @Override
    public List<PromotableOrderItem> getAllOrderItems() {
        if (allOrderItems == null) {
            allOrderItems = new ArrayList<>();

            for (OrderItem orderItem : order.getOrderItems()) {
                addPromotableOrderItem(orderItem, allOrderItems);
            }
        }

        return allOrderItems;
    }

    private List<PromotableOrderItem> buildPromotableOrderItemsList() {
        List<PromotableOrderItem> discountableOrderItems = new ArrayList<>();

        for (PromotableOrderItem promotableOrderItem : getAllOrderItems()) {
            if (promotableOrderItem.isDiscountingAllowed()) {
                discountableOrderItems.add(promotableOrderItem);
            }
        }

        return discountableOrderItems;
    }

    @Override
    public List<PromotableOrderItem> getDiscountableOrderItems(boolean sortBySalePrice) {

        if (discountableOrderItems == null || discountableOrderItems.isEmpty()) {
            discountableOrderItems = buildPromotableOrderItemsList();

            OrderItemPriceComparator priceComparator = new OrderItemPriceComparator(sortBySalePrice);
            // Sort the items so that the highest priced ones are at the top
            Collections.sort(discountableOrderItems, priceComparator);
            currentSortParam = sortBySalePrice;
        }

        if (currentSortParam != sortBySalePrice) {
            // Resort
            OrderItemPriceComparator priceComparator = new OrderItemPriceComparator(sortBySalePrice);
            Collections.sort(discountableOrderItems, priceComparator);

            currentSortParam = sortBySalePrice;
        }

        return discountableOrderItems;
    }

    @Override
    public List<PromotableOrderItem> getDiscountableOrderItems() {
        return getDiscountableOrderItems(currentSortParam);
    }

    @Override
    public List<PromotableFulfillmentGroup> getFulfillmentGroups() {
        if (fulfillmentGroups == null) {
            fulfillmentGroups = new ArrayList<>();

            FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();
            fulfillmentGroups.add(itemFactory.createPromotableFulfillmentGroup(fulfillmentGroup, this));
        }
        return Collections.unmodifiableList(fulfillmentGroups);
    }

    @Override
    public boolean isHasOrderAdjustments() {
        return candidateOrderOfferAdjustments.size() > 0;
    }

    @Override
    public List<PromotableOrderAdjustment> getCandidateOrderAdjustments() {
        return candidateOrderOfferAdjustments;
    }

    @Override
    public void addCandidateOrderAdjustment(PromotableOrderAdjustment orderAdjustment) {
        candidateOrderOfferAdjustments.add(orderAdjustment);
    }

    @Override
    public void removeAllCandidateOfferAdjustments() {
        removeAllCandidateItemOfferAdjustments();
        removeAllCandidateFulfillmentOfferAdjustments();
        removeAllCandidateOrderOfferAdjustments();
    }

    @Override
    public void removeAllCandidateOrderOfferAdjustments() {
        candidateOrderOfferAdjustments.clear();
    }

    @Override
    public void removeAllCandidateItemOfferAdjustments() {
        for (PromotableOrderItem promotableOrderItem : getDiscountableOrderItems()) {
            promotableOrderItem.removeAllItemAdjustments();
        }
    }

    @Override
    public void removeAllCandidateFulfillmentOfferAdjustments() {
        for (PromotableFulfillmentGroup fulfillmentGroup : getFulfillmentGroups()) {
            fulfillmentGroup.removeAllCandidateAdjustments();
        }
    }

    @Override
    public void updateRuleVariables(Map<String, Object> ruleVars) {
        ruleVars.put("order", order);
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public boolean isTotalitarianOfferApplied() {
        return isTotalitarianFgOfferApplied() || isTotalitarianItemOfferApplied()
                || isTotalitarianOrderOfferApplied();
    }

    @Override
    public int calculateOrderAdjustmentTotal() {
        int orderAdjustmentTotal = 0;
        for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
            orderAdjustmentTotal += adjustment.getAdjustmentValue();
        }
        return orderAdjustmentTotal;
    }

    @Override
    public int calculateItemAdjustmentTotal() {
        int itemAdjustmentTotal = 0;

        for (PromotableOrderItem item : getDiscountableOrderItems()) {
            itemAdjustmentTotal += item.calculateTotalAdjustmentValue();
        }
        return itemAdjustmentTotal;
    }

    @Override
    public List<PromotableOrderItemPriceDetail> getAllPromotableOrderItemPriceDetails() {
        List<PromotableOrderItemPriceDetail> allPriceDetails = new ArrayList<>();

        for (PromotableOrderItem item : getDiscountableOrderItems()) {
            allPriceDetails.addAll(item.getPromotableOrderItemPriceDetails());
        }
        return allPriceDetails;
    }

    private boolean isNotCombinableOrderOfferApplied() {
        for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
            if (!adjustment.isCombinable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canApplyOrderOffer(PromotableCandidateOrderOffer offer) {
        if (isNotCombinableOrderOfferApplied()) {
            return false;
        }

        if (!offer.isCombinable() || offer.isTotalitarian()) {
            // Only allow a combinable or totalitarian offer if this is the first adjustment.
            return candidateOrderOfferAdjustments.size() == 0;
        }
        return true;
    }

    @Override
    public Currency getOrderCurrency() {
        return order.getCurrency();
    }

    @Override
    public void setTotalFufillmentCharges(int totalFulfillmentCharges) {
        order.setTotalFulfillmentCharges(totalFulfillmentCharges);
    }

    @Override
    public int calculateSubtotalWithoutAdjustments() {
        int  calculatedSubTotal = 0;

        for (PromotableOrderItem orderItem : getAllOrderItems()) {
            calculatedSubTotal += orderItem.calculateTotalWithoutAdjustments();
        }
        return calculatedSubTotal;
    }

    @Override
    public int calculateSubtotalWithAdjustments() {
        int calculatedSubTotal = 0;

        for (PromotableOrderItem orderItem : getAllOrderItems()) {
            calculatedSubTotal += orderItem.calculateTotalWithAdjustments();
        }
        return calculatedSubTotal;
    }

    @Override
    public boolean isIncludeOrderAndItemAdjustments() {
        return includeOrderAndItemAdjustments;
    }

    @Override
    public boolean isTotalitarianOrderOfferApplied() {
        for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
            if (adjustment.isTotalitarian()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTotalitarianItemOfferApplied() {
        for (PromotableOrderItemPriceDetail itemPriceDetail : getAllPromotableOrderItemPriceDetails()) {
            if (itemPriceDetail.isTotalitarianOfferApplied()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTotalitarianFgOfferApplied() {
        for (PromotableFulfillmentGroup fg : getFulfillmentGroups()) {
            if (fg.isTotalitarianOfferApplied()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getExtraDataMap() {
        return extraDataMap;
    }

    @Override
    public String toString() {
        return "PromotableOrderImpl{" +
                ", order=" + order +
                ", allOrderItems=" + allOrderItems +
                ", discountableOrderItems=" + discountableOrderItems +
                ", currentSortParam=" + currentSortParam +
                ", fulfillmentGroups=" + fulfillmentGroups +
                ", candidateOrderOfferAdjustments=" + candidateOrderOfferAdjustments +
                ", includeOrderAndItemAdjustments=" + includeOrderAndItemAdjustments +
                ", extraDataMap=" + extraDataMap +
                '}';
    }
}