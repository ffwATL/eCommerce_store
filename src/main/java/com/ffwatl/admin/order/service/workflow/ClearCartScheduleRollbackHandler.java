package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.service.CartRollback;
import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import com.ffwatl.common.schedule.service.SingleTimeTimerTaskService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Component("clear_cart_schedule_rollback_handler")
@Scope("prototype")
public class ClearCartScheduleRollbackHandler implements RollbackHandler<CartOperationRequest> {

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService singleTimeTimerTaskService;

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CartOperationRequest>> activity,
                              ProcessContext<CartOperationRequest> processContext,
                              Map<String, Object> stateConfiguration) throws RollbackFailureException {

        if(!shouldExecute(stateConfiguration)){
            return;
        }
        Long orderId = (Long) stateConfiguration.get(CartRollback.ROLLBACK_CART_ID);

        singleTimeTimerTaskService.cancelAndRemoveTask(orderId);

    }

    /**
     * Returns true if this rollback handler should execute
     */
    private boolean shouldExecute(Map<String, Object> stateConfiguration) {
        return stateConfiguration != null && (
                stateConfiguration.get(CartRollback.PREVIOUS_TASK) == null &&
                        stateConfiguration.get(CartRollback.ROLLBACK_CART_ID) != null
        );
    }
}