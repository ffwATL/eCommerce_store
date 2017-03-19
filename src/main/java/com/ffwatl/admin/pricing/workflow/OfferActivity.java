package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferService;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class OfferActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name = "offer_service")
    private OfferService offerService;

    @Resource(name = "order_service")
    private OrderService orderService;


    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        List<OfferCode> offerCodes = offerService.buildOfferCodeListForCustomer(order.getCustomer());

        if (offerCodes != null && !offerCodes.isEmpty()) {
            order = orderService.addOfferCode(order, offerCodes.get(0), false);
        }

        List<Offer> offers = offerService.buildOfferListForOrder(order);
        order = offerService.applyAndSaveOffersToOrder(offers, order);
        context.setSeedData(order);

        return context;
    }
}