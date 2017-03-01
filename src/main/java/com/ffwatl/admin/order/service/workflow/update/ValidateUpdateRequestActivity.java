package com.ffwatl.admin.order.service.workflow.update;

import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class ValidateUpdateRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {


    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Throw an exception if the user did not specify an orderItemId
        if (orderItemRequestDTO.getOrderItemId() < 1) {
            throw new IllegalArgumentException("OrderItemId must be specified when removing from order");
        }

        // Throw an exception if the user tried to update an item to a negative quantity
        if (orderItemRequestDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        // Throw an exception if the user did not specify an order to add the item to
        if (request.getOrder() == null) {
            throw new IllegalArgumentException("Order is required when updating item quantities");
        }

        return context;
    }
}
