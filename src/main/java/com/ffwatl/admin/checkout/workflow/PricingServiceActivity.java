package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.PricingService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class PricingServiceActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {


    @Resource(name = "pricing_service")
    private PricingService pricingService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        Order order = pricingService.executePricing(seed.getOrder());
        seed.setOrder(order);

        return context;
    }
}
