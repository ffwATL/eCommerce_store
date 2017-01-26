package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.order.domain.OrderItemPriceDetail;

import java.io.Serializable;

public interface OrderItemPriceDetailAdjustment extends Serializable{

    OrderItemPriceDetailAdjustment init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason);


    long getId();

    Offer getOffer();

    String getReason();

    int getAdjustmentValue();

    /**
     * Returns the name of the offer at the time the adjustment was made.
     * @return the name of the offer at the time the adjustment was made.
     */
    String getOfferName();

    OrderItemPriceDetail getOrderItemPriceDetail();

    /**
     * Even for items that are on sale, it is possible that an adjustment was made
     * to the retail price that gave the customer a better offer.
     *
     * Since some offers can be applied to the sale price and some only to the
     * retail price, this setting provides the required value.
     *
     * @return true if this adjustment was applied to the sale price
     */
    boolean isAppliedToSalePrice();

    /**
     * Value of this adjustment relative to the retail price.
     * @return Value of this adjustment relative to the retail price.
     */
    int getRetailPriceValue();

    /**
     * Value of this adjustment relative to the sale price.
     * @return Value of this adjustment relative to the sale price.
     */
    int getSalesPriceValue();

    OrderItemPriceDetailAdjustment setId(long id);

    OrderItemPriceDetailAdjustment setOffer(Offer offer);

    OrderItemPriceDetailAdjustment setAdjustmentValue(int value);

    OrderItemPriceDetailAdjustment setReason(String reason);

    /**
     * Stores the offer name at the time the adjustment was made. Primarily to simplify display
     * within the admin.
     */
    OrderItemPriceDetailAdjustment setOfferName(String offerName);

    OrderItemPriceDetailAdjustment setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail);

    OrderItemPriceDetailAdjustment setAppliedToSalePrice(boolean appliedToSalePrice);

    OrderItemPriceDetailAdjustment setRetailPriceValue(int retailPriceValue);

    OrderItemPriceDetailAdjustment setSalesPriceValue(int salesPriceValue);

}