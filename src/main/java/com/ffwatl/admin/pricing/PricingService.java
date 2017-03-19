package com.ffwatl.admin.pricing;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.exception.PricingException;

/**
 * @author ffw_ATL.
 */
public interface PricingService {

    Order executePricing(Order order) throws PricingException;
}