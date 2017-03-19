package com.ffwatl.admin.pricing;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.Processor;
import com.ffwatl.admin.workflow.WorkflowException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("pricing_service")
public class PricingServiceImpl implements PricingService {

    @Resource(name="pricingWorkflow")
    protected Processor pricingWorkflow;

    @Override
    @SuppressWarnings("unchecked")
    public Order executePricing(Order order) throws PricingException {
        try {
            ProcessContext<Order> context = (ProcessContext<Order>) pricingWorkflow.doActivities(order);

            return context.getSeedData();
        } catch (WorkflowException e) {
            throw new PricingException("Unable to execute pricing for order -- id: " + order.getId(), e);
        }
    }
}