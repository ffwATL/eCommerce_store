package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.OrderItem;

/**
 * OrderItem level offer that has been qualified for an order,
 * but may still be ejected based on additional pricing
 * and stackability concerns once the order has been processed
 * through the promotion engine.
 */
public interface CandidateItemOffer {

    long getId();

    OrderItem getOrderItem();

    int getDiscountedPrice();

    Offer getOffer();


    CandidateItemOffer setId(long id);

    CandidateItemOffer setOrderItem(OrderItem orderItem);

    CandidateItemOffer setOffer(Offer offer);

    CandidateItemOffer setDiscountedPrice(int discountedPrice);

}