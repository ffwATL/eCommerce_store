package com.ffwatl.admin.checkout;

import com.ffwatl.admin.checkout.exception.CheckoutException;
import com.ffwatl.admin.checkout.workflow.CheckoutResponse;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderStatus;

/**
 * @author ffw_ATL.
 */
public interface CheckoutService {

    /**
     * <p>Checks out an order by executing the blCheckoutWorkflow. The <b>order</b> is saved both before and after the workflow
     * is executed so that activities can modify the various entities on and related to the <b>order</b>.</p>
     *
     * <p>This method is also thread-safe; 2 requests cannot attempt to check out the same <b>order</b></p>
     *
     * @param order the order to be checked out
     *
     * @throws CheckoutException if there are any exceptions while executing any of the activities in the workflow (assuming
     * that the workflow does not already have a preconfigured error handler) or if the given <b>order</b> has already been
     * checked out (in Broadleaf terms this means the <b>order</b> has already been changed to {@link OrderStatus#SUBMITTED})
     */
    CheckoutResponse performCheckout(Order order) throws CheckoutException;
}
