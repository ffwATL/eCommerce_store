package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.Order;

import java.io.Serializable;


public interface CandidateOrderOffer extends Serializable {

    long getId();
    int getDiscountedPrice();
    Order getOrder();
    Offer getOffer();

    CandidateOrderOffer setId(long id);
    CandidateOrderOffer setDiscountedPrice(int discountedPrice);
    CandidateOrderOffer setOrder(Order order);
    CandidateOrderOffer setOffer(Offer offer);
}