package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.workflow.DefaultProcessContextImpl;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.ProcessContextFactory;
import com.ffwatl.admin.workflow.WorkflowException;

/**
 * @author ffw_ATL.
 */
public class CheckoutProcessContextFactory implements ProcessContextFactory<CheckoutSeed, CheckoutSeed> {

    @Override
    public ProcessContext<CheckoutSeed> createContext(CheckoutSeed preSeedData) throws WorkflowException {
        ProcessContext<CheckoutSeed> context = new DefaultProcessContextImpl<>();
        context.setSeedData(preSeedData);

        return context;
    }
}