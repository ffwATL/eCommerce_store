package com.ffwatl.admin.order.service.workflow.remove;

import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.order.strategy.FulfillmentGroupItemStrategy;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

/**
 * @author ffw_ATL.
 */
public class RemoveFulfillmentGroupItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {


    private FulfillmentGroupItemStrategy fgItemStrategy;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        request = fgItemStrategy.onItemRemoved(request);

        context.setSeedData(request);
        return context;
    }
}