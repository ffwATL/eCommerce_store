package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.offer.service.ShippingOfferService;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class ShippingOfferActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name="shipping_offer_service")
    private ShippingOfferService shippingOfferService;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        shippingOfferService.reviewOffers(order);
        context.setSeedData(order);

        return context;
    }
}
