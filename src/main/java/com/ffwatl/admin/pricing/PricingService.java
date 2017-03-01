package com.ffwatl.admin.pricing;

import com.ffwatl.admin.order.domain.Order;

/**
 * @author ffw_ATL.
 */
public interface PricingService {

    Order executePricing(Order order) /*throws PricingException*/;
}