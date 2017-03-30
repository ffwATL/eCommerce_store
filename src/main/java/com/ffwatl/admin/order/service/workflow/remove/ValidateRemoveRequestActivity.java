package com.ffwatl.admin.order.service.workflow.remove;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class ValidateRemoveRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Throw an exception if the user did not specify an orderItemId
        if (orderItemRequestDTO.getOrderItemId() < 1) {
            throw new IllegalArgumentException("OrderItemId must be specified in valid way when removing from order");
        }

        // Throw an exception if the user did not specify an order to add the item to
        if (request.getOrder() == null) {
            throw new IllegalArgumentException("Order is required when removing item from the order");
        }

        OrderItem orderItem = null;
        for (OrderItem oi : request.getOrder().getOrderItems()) {
            if (oi.getId() == orderItemRequestDTO.getOrderItemId()) {
                orderItem = oi;
                break;
            }
        }

        if (orderItem == null) {
            throw new IllegalArgumentException("Could not find order item to remove");
        }

        request.setOrderItem(orderItem);

        return context;
    }
}
