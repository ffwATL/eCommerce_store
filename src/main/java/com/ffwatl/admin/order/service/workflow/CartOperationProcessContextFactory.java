package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.workflow.DefaultProcessContextImpl;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.ProcessContextFactory;
import com.ffwatl.admin.workflow.WorkflowException;

/**
 * @author ffw_ATL.
 */
public class CartOperationProcessContextFactory implements ProcessContextFactory<CartOperationRequest, CartOperationRequest> {

    @Override
    public ProcessContext<CartOperationRequest> createContext(CartOperationRequest preSeedData) throws WorkflowException {
        if (preSeedData == null){
            throw new WorkflowException("Seed data instance is incorrect. Null");
        }

        ProcessContext<CartOperationRequest> context = new DefaultProcessContextImpl<>();
        context.setSeedData(preSeedData);
        return context;
    }
}
