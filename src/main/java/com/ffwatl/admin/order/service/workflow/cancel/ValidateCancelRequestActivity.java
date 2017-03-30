package com.ffwatl.admin.order.service.workflow.cancel;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class ValidateCancelRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        Order order = request.getOrder();

        if(order == null){
            throw new IllegalArgumentException("Order is required when canceling the order");
        }

        if(order.getOrderItems() == null || order.getOrderItems().size() < 1) {
            throw new IllegalArgumentException("Order has no order items. Nothing to delete");
        }

        return context;
    }
}