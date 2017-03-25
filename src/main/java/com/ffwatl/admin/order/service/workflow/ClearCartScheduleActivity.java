package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.schedule.SingleTimeTimerTask;
import com.ffwatl.common.schedule.SingleTimeTimerTaskManager;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ffw_ATL.
 */
public class ClearCartScheduleActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "orderSingleTimeTimerTaskManager")
    private SingleTimeTimerTaskManager taskManager;

    @Resource(name = "order_service")
    private OrderService orderService;

    private long minutesDelay;


    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        long orderId = request.getOrder().getId();

        SingleTimeTimerTask timerTask = orderService.createOrderSingleTimeTimerTask(orderId);
        /*timerTask.setStartDateTime(LocalDateTime.now().plusMinutes(minutesDelay));*/
        timerTask.setStartDateTime(LocalDateTime.now().plusSeconds(minutesDelay));

        taskManager.updateTask(orderId, timerTask);
        return context;
    }

    public void setMinutesDelay(long minutesDelay) {
        this.minutesDelay = minutesDelay;
    }
}