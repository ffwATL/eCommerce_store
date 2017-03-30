package com.ffwatl.admin.order.service.workflow.remove;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.OrderItemService;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class RemoveOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        OrderItem orderItem = request.getOrderItem();
        request.getOisToDelete().add(orderItem);

        for(OrderItem oi: request.getOisToDelete()){
            orderItemService.delete(oi);
        }

        return context;
    }
}