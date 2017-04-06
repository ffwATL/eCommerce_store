package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.schedule.service.SingleTimeTimerTaskService;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class RemoveClearCartScheduleActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService singleTimeTimerTaskService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();


        if(seed == null || seed.getOrder() == null){
            throw new IllegalArgumentException("Wrong seed data is given");
        }

        Order order = seed.getOrder();

        singleTimeTimerTaskService.cancelAndRemoveTask(order.getId());

        return context;
    }
}