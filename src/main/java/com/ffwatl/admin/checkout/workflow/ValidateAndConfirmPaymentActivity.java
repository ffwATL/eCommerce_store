package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * p>Verifies that there is enough payment on the order via the <i>successful</i> amount on transactions
 *
 * <p>If there is an exception (either in this activity or later downstream) the confirmed payments are rolled back via {@link GenericConfirmPaymentsRollbackHandler}
 * @author ffw_ATL.
 */
public class ValidateAndConfirmPaymentActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {


    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        // it does nothing for now
        return context;
    }
}