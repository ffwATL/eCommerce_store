package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PromotableOrderItemImpl implements PromotableOrderItem {

    private static final Logger logger = LogManager.getLogger();

    private PromotableOrder promotableOrder;
    private OrderItem orderItem;
    private PromotableItemFactory itemFactory;
    private List<PromotableOrderItemPriceDetail> itemPriceDetails = new ArrayList<>();
    private boolean includeAdjustments;
    private Map<String, Object> extraDataMap = new HashMap<>();

    public PromotableOrderItemImpl(OrderItem orderItem, PromotableOrder promotableOrder,
                                   PromotableItemFactory itemFactory, boolean includeAdjustments) {
        this.orderItem = orderItem;
        this.promotableOrder = promotableOrder;
        this.itemFactory = itemFactory;
        this.includeAdjustments = includeAdjustments;
        initializePriceDetails();
    }

    private void initializePriceDetails() {

        if (includeAdjustments) {
            for (OrderItemPriceDetail detail : orderItem.getOrderItemPriceDetails()) {
                PromotableOrderItemPriceDetail poid =
                        itemFactory.createPromotableOrderItemPriceDetail(this, detail.getQuantity());
                itemPriceDetails.add(poid);
                poid.chooseSaleOrRetailAdjustments();
                for (OrderItemPriceDetailAdjustment adjustment : detail.getOrderItemPriceDetailAdjustments()) {
                    PromotableOrderItemPriceDetailAdjustment poidAdj =
                            new PromotableOrderItemPriceDetailAdjustmentImpl(adjustment, poid);
                    poid.addCandidateItemPriceDetailAdjustment(poidAdj);
                }

                Set<OrderItemQualifier> oiqs = poid.getPromotableOrderItem().getOrderItem().getOrderItemQualifiers();
                if (CollectionUtils.isNotEmpty(oiqs)) {
                    for (OrderItemQualifier oiq : oiqs) {
                        PromotionQualifier pq = new PromotionQualifierImpl();
                        pq.setPromotion(oiq.getOffer());
                        pq.setQuantity(oiq.getQuantity());
                        pq.setFinalizedQuantity(oiq.getQuantity());
                        poid.getPromotionQualifiers().add(pq);
                    }
                }
            }
        } else {
            PromotableOrderItemPriceDetail poid =
                    itemFactory.createPromotableOrderItemPriceDetail(this, orderItem.getQuantity());
            itemPriceDetails.add(poid);
        }
    }


    @Override
    public void updateRuleVariables(Map<String, Object> ruleVars) {
        ruleVars.put("orderItem", orderItem);

    }

    @Override
    public void resetPriceDetails() {
        itemPriceDetails.clear();
        initializePriceDetails();

    }

    @Override
    public boolean isDiscountingAllowed() {
        return orderItem.isDiscountingAllowed();
    }

    @Override
    public int getSalePriceBeforeAdjustments() {
        return orderItem.getSalePrice();
    }

    @Override
    public int getRetailPriceBeforeAdjustments() {
        return orderItem.getRetailPrice();
    }

    @Override
    public boolean isOnSale() {
        return orderItem.getIsOnSale();
    }

    @Override
    public List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails() {
        return itemPriceDetails;
    }

    @Override
    public int getPriceBeforeAdjustments(boolean applyToSalePrice) {
        if (applyToSalePrice && getSalePriceBeforeAdjustments() > 0) {
            return getSalePriceBeforeAdjustments();
        }
        return getRetailPriceBeforeAdjustments();
    }

    @Override
    public int getCurrentBasePrice() {
        if (orderItem.getIsOnSale()) {
            return orderItem.getSalePrice();
        } else {
            return orderItem.getRetailPrice();
        }
    }

    @Override
    public int getQuantity() {
        return orderItem.getQuantity();
    }

    @Override
    public Currency getCurrency() {
        return orderItem.getOrder().getCurrency();
    }

    @Override
    public void removeAllItemAdjustments() {
        Iterator<PromotableOrderItemPriceDetail> detailIterator = itemPriceDetails.iterator();
        boolean first = true;

        while (detailIterator.hasNext()) {
            PromotableOrderItemPriceDetail detail = detailIterator.next();

            if (first) {
                detail.setQuantity(getQuantity());
                detail.getPromotionDiscounts().clear();
                detail.getPromotionQualifiers().clear();
                detail.removeAllAdjustments();

                first = false;
            } else {
                // Get rid of all other details
                detailIterator.remove();
            }
        }
    }

    private void mergeDetails(PromotableOrderItemPriceDetail firstDetail, PromotableOrderItemPriceDetail secondDetail) {
        int firstQty = firstDetail.getQuantity();
        int secondQty = secondDetail.getQuantity();

        if (logger.isDebugEnabled()) {
            logger.trace("Merging priceDetails with quantities " + firstQty + " and " + secondQty);
        }

        firstDetail.setQuantity(firstQty + secondQty);
    }

    @Override
    public void mergeLikeDetails() {
        if (itemPriceDetails.size() > 1) {
            Iterator<PromotableOrderItemPriceDetail> detailIterator = itemPriceDetails.iterator();
            Map<String, PromotableOrderItemPriceDetail> detailMap = new HashMap<String, PromotableOrderItemPriceDetail>();

            while (detailIterator.hasNext()) {
                PromotableOrderItemPriceDetail currentDetail = detailIterator.next();
                String detailKey = currentDetail.buildDetailKey();
                if (detailMap.containsKey(detailKey)) {
                    PromotableOrderItemPriceDetail firstDetail = detailMap.get(detailKey);
                    mergeDetails(firstDetail, currentDetail);
                    detailIterator.remove();
                } else {
                    detailMap.put(detailKey, currentDetail);
                }
            }
        }
    }

    @Override
    public long getOrderItemId() {
        return orderItem.getId();
    }

    @Override
    public int calculateTotalAdjustmentValue() {
        int returnTotal = 0;

        for (PromotableOrderItemPriceDetail detail : itemPriceDetails) {
            returnTotal += detail.calculateTotalAdjustmentValue();
        }
        return returnTotal;
    }

    @Override
    public int calculateTotalWithAdjustments() {
        int returnTotal = 0;

        for (PromotableOrderItemPriceDetail detail : itemPriceDetails) {
            returnTotal += detail.getFinalizedTotalWithAdjustments();
        }
        return returnTotal;
    }

    @Override
    public int calculateTotalWithoutAdjustments() {
        return getCurrentBasePrice() * orderItem.getQuantity();
    }

    @Override
    public PromotableOrderItemPriceDetail createNewDetail(int quantity) {
        if (includeAdjustments) {
            throw new RuntimeException("Trying to createNewDetail when adjustments have already been included.");
        }
        return itemFactory.createPromotableOrderItemPriceDetail(this, quantity);
    }

    @Override
    public OrderItem getOrderItem() {
        return orderItem;
    }

    @Override
    public Map<String, Object> getExtraDataMap() {
        return extraDataMap;
    }

    @Override
    public String toString() {
        return "PromotableOrderItemImpl{" +
                "promotableOrder=" + promotableOrder +
                ", orderItem=" + orderItem +
                ", itemFactory=" + itemFactory +
                ", itemPriceDetails=" + itemPriceDetails +
                ", includeAdjustments=" + includeAdjustments +
                ", extraDataMap=" + extraDataMap +
                '}';
    }
}