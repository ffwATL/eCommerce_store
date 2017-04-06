package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
public class FulfillmentItemPricingActivity extends BaseActivity<ProcessContext<Order>> {


    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        Order order = context.getSeedData();
        Map<OrderItem, FulfillmentGroupItem> partialOrderItemMap = new HashMap<>();

        // Calculate the fulfillmentGroupItem total
        populateItemTotalAmount(order, partialOrderItemMap);

        // Calculate the fulfillmentGroupItem prorated orderSavings
        int totalAllItemsAmount = calculateTotalPriceForAllFulfillmentItems(order);
        int totalOrderAdjustmentDistributed = distributeOrderSavingsToItems(order, totalAllItemsAmount);
        /*fixOrderSavingsRoundingIssues(order, totalOrderAdjustmentDistributed);*/

        // Step 3: Finalize the taxable amounts
        /*updateTaxableAmountsOnItems(order);*/
        context.setSeedData(order);

        return context;
    }


    /**
     * Returns the total price for all fulfillment items.
     */
    private int calculateTotalPriceForAllFulfillmentItems(Order order) {
        int totalAllItemsAmount = 0;
        FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();

        if(fulfillmentGroup != null){
            for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                totalAllItemsAmount += fgItem.getTotalItemAmount();
            }
        }

        return totalAllItemsAmount;
    }

    /**
     * Distributes the order adjustments (if any) to the individual fulfillment group items.
     */
    private int distributeOrderSavingsToItems(Order order, int totalAllItems) {
        int returnAmount = 0;

        int orderAdjAmt = order.getOrderAdjustmentsValue();
        FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();

        if(fulfillmentGroup != null){
            for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                int fgItemAmount = fgItem.getTotalItemAmount();
                int proratedAdjAmt = totalAllItems == 0 ? totalAllItems : (orderAdjAmt * fgItemAmount / totalAllItems);
                fgItem.setProratedOrderAdjustmentAmount(proratedAdjAmt);
                returnAmount += fgItem.getProratedOrderAdjustmentAmount();
            }
        }

        return returnAmount;
    }

    /**
     * Sets the fulfillment amount which includes the relative portion of the total price for
     * the corresponding order item.
     */
    private void populateItemTotalAmount(Order order, Map<OrderItem, FulfillmentGroupItem> partialOrderItemMap) {
        FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();

        if(fulfillmentGroup == null) return;

        for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
            OrderItem orderItem = fgItem.getOrderItem();
            int totalItemAmount = orderItem.getTotalPrice();

            fgItem.setTotalItemAmount(totalItemAmount);
        }
    }
}