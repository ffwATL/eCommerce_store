package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.offer.domain.Offer;

public interface OrderItemQualifier {

    long getId();

    /**
     * The related order item.
     */
    OrderItem getOrderItem();

    /**
     * Returns the related offer
     */
    Offer getOffer();

    int getQuantity();


    OrderItemQualifier setId(long id);

    OrderItemQualifier setOrderItem(OrderItem orderItem);

    OrderItemQualifier setOffer(Offer offer);
    /**
     * Sets the quantity of the associated OrderItem that was used as a qualifier.
     */
    OrderItemQualifier setQuantity(int quantity);

}