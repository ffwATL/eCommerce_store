package com.ffwatl.admin.offer.service;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.exception.PricingException;

/**
 * @author ffw_ATL.
 */
public interface ShippingOfferService {

    void reviewOffers(Order order) throws PricingException;
}
