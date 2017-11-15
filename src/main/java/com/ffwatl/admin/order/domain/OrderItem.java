package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.CandidateItemOffer;

import java.io.Serializable;
import java.util.Set;

public interface OrderItem extends Serializable {

    /**
     * The unique identifier of this OrderItem.
     * @return the unique identifier of this OrderItem.
     */
    long getId();

    long getProductId();

    I18n getProductName();

    ProductAttributeType getProductAttributeType();

    ProductAttribute getProductAttribute();

    Color getColor();

    /**
     * Reference back to the containing order.
     * @return reference back to the containing order.
     */
    Order getOrder();

    int getOriginPrice();

    /**
     * The retail price of the item that was added to the {@link Order} at the time that this was added.
     * This is preferable to use as opposed to checking the price of the item that was added from the
     * catalog domain, since the price in the catalog domain could have changed since the item
     * was added to the {@link Order}.
     * @return the retail price of the item that was added to the {@link Order}.
     */
    int getRetailPrice();

    /**
     * Returns the salePrice for this item.
     * @return the salePrice for this item.
     */
    int getSalePrice();

    /**
     * The quantity of this {@link OrderItem}.
     * @return the quantity of this {@link OrderItem}.
     */
    int getQuantity();

    /**
     * Collection of priceDetails for this orderItem.
     * Without discounts, an orderItem would have exactly 1 ItemPriceDetail. When orderItem discounting or
     * tax-calculations result in an orderItem having multiple prices like in a buy-one-get-one free example,
     * the orderItem will get an additional ItemPriceDetail.
     * Generally, an OrderItem will have 1 ItemPriceDetail record for each uniquely priced version of the item.
     */
    Set<OrderItemPriceDetail> getOrderItemPriceDetails();

    ProductCategory getProductCategory();

    Set<CandidateItemOffer> getCandidateItemOffers();

    /**
     * If any quantity of this item was used to qualify for an offer, then this returned list
     * will indicate the offer and the relevant quantity.
     * As an example, a BuyOneGetOneFree offer would have 1 qualifier and 1 adjustment.
     * @return a List of OrderItemAdjustment.
     */
    Set<OrderItemQualifier> getOrderItemQualifiers();

    /**
     * Return true if this item is on sale;
     * @return true if this item is on sale.
     */
    boolean getIsOnSale();

    /**
     * If true, this item can be discounted..
     */
    boolean isDiscountingAllowed();

    /**
     * Returns true if this item received a discount.
     * @return true if this item received a discount.
     */
    boolean getIsDiscounted();

    /**
     * Returns the total price to be paid for this order item including item-level adjustments.
     * It does not include the effect of order level adjustments. Calculated by looping through
     * the orderItemPriceDetails
     * @return the total price to be paid for this order item including item-level adjustments.
     */
    int getTotalPrice();

    /**
     * Returns the discount value in percent that was applied to this orderItem.
     * @return the discount value in percent that was applied to this orderItem.
     */
    int getDiscountValue();

    /**
     * Returns the total price to be paid before adjustments.
     */
    int getTotalPriceBeforeAdjustments();

    int getPriceBeforeAdjustments(boolean allowSalesPrice);


    OrderItem setId(long id);

    OrderItem setProductId(long id);

    OrderItem setProductName(I18n productName);

    OrderItem setProductAttributeType(ProductAttributeType productAttributeType);

    OrderItem setProductAttribute(ProductAttribute productAttribute);

    OrderItem setColor(Color color);

    OrderItem setOrder(Order order);

    OrderItem setOriginPrice(int originPrice);

    OrderItem setRetailPrice(int retailPrice);

    OrderItem setSalePrice(int salePrice);

    OrderItem setQuantity(int quantity);

    OrderItem setOrderItemPriceDetails(Set<OrderItemPriceDetail> orderItemPriceDetails);

    OrderItem setProductCategory(ProductCategory productCategory);

    OrderItem setCandidateItemOffers(Set<CandidateItemOffer> candidateItemOffers);

    OrderItem setOrderItemQualifiers(Set<OrderItemQualifier> orderItemQualifiers);

    OrderItem setDiscountingAllowed(boolean discountingAllowed);

    OrderItem setDiscountValue(int discountValue);

    /**
     * Used by the promotion engine to add offers that might apply to this orderItem.
     */
    OrderItem addCandidateItemOffer(CandidateItemOffer candidateItemOffer);

    /**
     * Removes all candidate offers.   Used by the promotion engine which subsequently adds
     * the candidate offers that might apply back to this item.
     */
    OrderItem removeAllCandidateItemOffers();

    /**
     * Removes all adjustment for this order item and reset the adjustment price.
     */
    int removeAllAdjustments();

}