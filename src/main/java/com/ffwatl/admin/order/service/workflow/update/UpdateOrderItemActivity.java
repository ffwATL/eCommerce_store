package com.ffwatl.admin.order.service.workflow.update;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class UpdateOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        Order order = request.getOrder();

        OrderItem orderItem = null;
        for (OrderItem oi : order.getOrderItems()) {
            if (oi.getId() == orderItemRequestDTO.getOrderItemId()) {
                orderItem = oi;
            }
        }

        if (orderItem == null || !order.getOrderItems().contains(orderItem)) {
            throw new IllegalArgumentException("Order Item (" + orderItemRequestDTO.getOrderItemId() + ") not found in Order (" + order.getId() + ")");
        }

        OrderItem itemFromOrder = order.getOrderItems().get(order.getOrderItems().indexOf(orderItem));
        if (orderItemRequestDTO.getQuantity() >= 0) {
            request.setOrderItemQuantityDelta(orderItemRequestDTO.getQuantity() - itemFromOrder.getQuantity());
            itemFromOrder.setQuantity(orderItemRequestDTO.getQuantity());
            request.setOrderItem(itemFromOrder);
        }

        return context;
    }
}