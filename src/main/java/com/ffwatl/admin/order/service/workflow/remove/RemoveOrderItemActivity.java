package com.ffwatl.admin.order.service.workflow.remove;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class RemoveOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        OrderItem orderItem = request.getOrderItem();
        request.getOisToDelete().add(orderItem);

        return context;
    }
}