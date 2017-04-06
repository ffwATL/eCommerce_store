package com.ffwatl.admin.order.service.workflow.update;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.OrderItemService;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class UpdateOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();
        Order order = request.getOrder();
        List<OrderItem> orderItemList = order.getOrderItems();

        OrderItem orderItem = null;
        for (OrderItem oi : orderItemList) {
            if (oi.getId() == orderItemRequestDTO.getOrderItemId()) {
                orderItem = oi;
            }
        }

        if (orderItem == null || !orderItemList.contains(orderItem)) {
            throw new IllegalArgumentException("Order Item (" + orderItemRequestDTO.getOrderItemId() + ") not found in Order (" + order.getId() + ")");
        }

        OrderItem itemFromOrder = orderItemList.get(orderItemList.indexOf(orderItem));
        int qty = orderItemRequestDTO.getQuantity();

        if(orderItemRequestDTO.isIncrementOrderItemQuantity()){
            qty = itemFromOrder.getQuantity() + qty;
        }else {
            qty = itemFromOrder.getQuantity() - qty;
        }

        if(qty == 0){
            orderItemList.remove(itemFromOrder);
        }else {
            itemFromOrder.setQuantity(qty);
        }

        return context;
    }
}