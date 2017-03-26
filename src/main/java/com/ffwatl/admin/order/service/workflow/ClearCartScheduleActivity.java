package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.service.CartRollback;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.ActivityStateManagerImpl;
import com.ffwatl.common.schedule.service.SingleTimeTimerTask;
import com.ffwatl.common.schedule.service.SingleTimeTimerTaskService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
public class ClearCartScheduleActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService singleTimeTimerTaskService;

    private long minutesDelay = -1;

    private long secondsDelay = -1;


    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        long orderId = request.getOrder().getId();

        SingleTimeTimerTask timerTask = singleTimeTimerTaskService.createTask(orderId);

        if(minutesDelay > 0){
            timerTask.setStartDateTime(LocalDateTime.now().plusMinutes(minutesDelay));
        }else{
            timerTask.setStartDateTime(LocalDateTime.now().plusSeconds(secondsDelay));
        }

        SingleTimeTimerTask previousTask = singleTimeTimerTaskService.addTask(timerTask);

        Map<String, Object> rollbackState = new HashMap<>();
        if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
            if (getStateConfiguration() != null && !getStateConfiguration().isEmpty()) {
                rollbackState.putAll(getStateConfiguration());
            }
            // Register the map with the rollback state object early on; this allows the extension handlers
            // to incrementally add state while decrementing but still throw an exception
            ActivityStateManagerImpl
                    .getStateManager()
                    .registerState(this, context, getRollbackRegion(), getRollbackHandler(), rollbackState);
        }

        rollbackState.put(CartRollback.PREVIOUS_TASK, previousTask);
        rollbackState.put(CartRollback.ROLLBACK_CART_ID, orderId);

        return context;
    }

    public void setMinutesDelay(long minutesDelay) {
        this.minutesDelay = minutesDelay;
    }

    public void setSecondsDelay(long secondsDelay) {
        this.secondsDelay = secondsDelay;
    }
}