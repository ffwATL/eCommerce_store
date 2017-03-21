package com.ffwatl.admin.order.service.workflow.remove;

import com.ffwatl.admin.order.service.FulfillmentGroupService;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.order.strategy.FulfillmentGroupItemStrategy;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class RemoveFulfillmentGroupItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "fulfillment_group_item_strategy")
    private FulfillmentGroupItemStrategy fgItemStrategy;

    @Resource(name = "fulfillment_group_service")
    private FulfillmentGroupService fulfillmentGroupService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        request = fgItemStrategy.onItemRemoved(request);

        fulfillmentGroupService.removeFulfillmentGroupItems(request.getFgisToDelete());

        context.setSeedData(request);
        return context;
    }
}