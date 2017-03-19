package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Rollback handler to execute after an order has been marked as 'completed' and there is an exception.
 *
 *  1. Change the status back to IN_PROCESS
 *  2. Change the order number back to null
 *  3. Change the submit date back to null
 *
 * @author ffw_ATL.
 */
@Component("complete_order_rollback_handler")
public class CompleteOrderRollbackHandler implements RollbackHandler<CheckoutSeed> {

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity,
                              ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        CheckoutSeed seed = processContext.getSeedData();
        Order order = seed.getOrder();

        order.setOrderStatus(OrderStatus.IN_PROCESS);
        order.setOrderNumber(null);
        order.setSubmitDate(null);
    }
}