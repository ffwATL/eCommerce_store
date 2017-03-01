package com.ffwatl.admin.order.strategy;

import com.ffwatl.admin.order.service.workflow.CartOperationRequest;

/**
 * @author ffw_ATL.
 */
public interface FulfillmentGroupItemStrategy {

    CartOperationRequest onItemAdded(CartOperationRequest request) /*throws PricingException*/;

    CartOperationRequest onItemUpdated(CartOperationRequest request) /*throws PricingException*/;

    CartOperationRequest onItemRemoved(CartOperationRequest request) /*throws PricingException*/;

    CartOperationRequest verify(CartOperationRequest request) /*throws PricingException*/;

    void setRemoveEmptyFulfillmentGroups(boolean removeEmptyFulfillmentGroups);

    boolean isRemoveEmptyFulfillmentGroups();
}
