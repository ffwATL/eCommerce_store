package com.ffwatl.admin.pricing;

import com.ffwatl.admin.order.domain.Order;
import org.springframework.stereotype.Service;

/**
 * @author ffw_ATL.
 */
@Service("pricing_service")
public class PricingServiceImpl implements PricingService {

    @Override
    public Order executePricing(Order order) {
        throw new UnsupportedOperationException();
    }
}