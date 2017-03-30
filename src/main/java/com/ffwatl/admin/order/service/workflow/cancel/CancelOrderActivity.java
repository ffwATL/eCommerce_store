package com.ffwatl.admin.order.service.workflow.cancel;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.schedule.service.SingleTimeTimerTaskService;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class CancelOrderActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService taskService;

    @Resource(name = "order_service")
    private OrderService orderService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        Order order = request.getOrder();
        Long orderId = order.getId();

        order.getOrderItems().clear();
        orderService.finallyDeleteOrder(order, true);

        taskService.cancelAndRemoveTask(orderId);

        return context;
    }
}