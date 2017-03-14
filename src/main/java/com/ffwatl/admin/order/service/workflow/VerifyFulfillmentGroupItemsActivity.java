package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.strategy.FulfillmentGroupItemStrategy;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class VerifyFulfillmentGroupItemsActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "fulfillment_group_item_strategy")
    private FulfillmentGroupItemStrategy fgItemStrategy;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        request = fgItemStrategy.verify(request);

        context.setSeedData(request);
        return context;
    }
}
