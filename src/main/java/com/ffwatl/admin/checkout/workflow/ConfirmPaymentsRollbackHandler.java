package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Component("confirm_payments_rollback_handler")
public class ConfirmPaymentsRollbackHandler implements RollbackHandler<CheckoutSeed> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity,
                              ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {

        throw new UnsupportedOperationException();
    }
}