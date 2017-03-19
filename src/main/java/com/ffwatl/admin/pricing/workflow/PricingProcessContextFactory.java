package com.ffwatl.admin.pricing.workflow;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.workflow.DefaultProcessContextImpl;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.ProcessContextFactory;
import com.ffwatl.admin.workflow.WorkflowException;

/**
 * @author ffw_ATL.
 */
class PricingProcessContextFactory implements ProcessContextFactory<Order, Order> {

    @Override
    public ProcessContext<Order> createContext(Order preSeedData) throws WorkflowException {
        ProcessContext<Order> context = new DefaultProcessContextImpl<>();
        context.setSeedData(preSeedData);

        return context;
    }
}
