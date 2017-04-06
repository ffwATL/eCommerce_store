package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class PriceOrderIfNecessaryActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_service")
    private OrderService orderService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        Order order = request.getOrder();

        order = orderService.save(order, request.isPriceOrder());

        request.setOrder(order);
        return context;
    }
}