package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.FulfillmentPricingService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class FulfillmentGroupPricingActivity extends BaseActivity<ProcessContext<Order>> {

    @Resource(name = "fulfillment_pricing_service")
    private FulfillmentPricingService fulfillmentPricingService;

    public void setFulfillmentPricingService(FulfillmentPricingService fulfillmentPricingService) {
        this.fulfillmentPricingService = fulfillmentPricingService;
    }

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        /*
         * 1. Get FGs from Order
         * 2. take each FG and call shipping module with the shipping svc
         * 3. add FG back to order
         */

        int totalFulfillmentCharges = 0;
        FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();

        if (fulfillmentGroup != null) {

            fulfillmentGroup = fulfillmentPricingService.calculateCostForFulfillmentGroup(fulfillmentGroup);

            if (fulfillmentGroup.getFulfillmentPrice() != 0) {
                totalFulfillmentCharges += fulfillmentGroup.getFulfillmentPrice();
            }
        }
        order.setTotalFulfillmentCharges(totalFulfillmentCharges);
        context.setSeedData(order);

        return context;
    }
}