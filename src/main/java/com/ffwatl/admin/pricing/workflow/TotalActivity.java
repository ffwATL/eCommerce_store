package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class TotalActivity extends BaseActivity<ProcessContext<Order>> {

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();

        int total = 0;

        int subtotal = order.calculateSubTotal();

        order.setSubTotal(subtotal);

        total += subtotal;
        total -= order.getOrderAdjustmentsValue();
        total += order.getTotalFulfillmentCharges();

        order.setTotal(total);

        context.setSeedData(order);
        return context;
    }
}