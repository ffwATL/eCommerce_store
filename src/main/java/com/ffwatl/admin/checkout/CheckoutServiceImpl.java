package com.ffwatl.admin.checkout;

import com.ffwatl.admin.checkout.exception.CheckoutException;
import com.ffwatl.admin.checkout.workflow.CheckoutResponse;
import com.ffwatl.admin.checkout.workflow.CheckoutSeed;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.Processor;
import com.ffwatl.admin.workflow.WorkflowException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ffw_ATL.
 */
@Service("checkout_service")
public class CheckoutServiceImpl implements CheckoutService {

    @Resource(name = "checkoutWorkflow")
    private Processor checkoutWorkflow;

    @Resource(name = "order_service")
    private OrderService orderService;

    /**
     * Map of locks for given order ids. This lock map ensures that only a single request
     * can handle a particular order at a time
     */
    private static ConcurrentMap<Long, Object> lockMap = new ConcurrentHashMap<>();


    @Override
    @SuppressWarnings("unchecked")
    public CheckoutResponse performCheckout(Order order) throws CheckoutException {
        //Immediately fail if another thread is currently attempting to check out the order
        Object lockObject = putLock(order.getId());
        if (lockObject != null) {
            throw new CheckoutException("This order is already in the process of being submitted, " +
                    "unable to checkout order -- id: " + order.getId(), new CheckoutSeed(order, new HashMap<>()));
        }

        // Immediately fail if this order has already been checked out previously
        if (hasOrderBeenCompleted(order)) {
            throw new CheckoutException("This order has already been submitted or cancelled, " +
                    "unable to checkout order -- id: " + order.getId(), new CheckoutSeed(order, new HashMap<>()));
        }

        CheckoutSeed seed = null;
        try {
            // Do a final save of the order before going through with the checkout workflow
            order = orderService.save(order, false);
            seed = new CheckoutSeed(order, new HashMap<>());

            ProcessContext<CheckoutSeed> context = (ProcessContext<CheckoutSeed>) checkoutWorkflow.doActivities(seed);

            // We need to pull the order off the seed and save it here in case any activity modified the order.
            order = orderService.save(seed.getOrder(), false);
            /*order.getOrderMessages().addAll(((ActivityMessages) context).getActivityMessages());*/
            seed.setOrder(order);

            return seed;
        } catch (PricingException e) {
            throw new CheckoutException("Unable to checkout order -- id: " + order.getId(), e, seed);
        } catch (WorkflowException e) {
            throw new CheckoutException("Unable to checkout order -- id: " + order.getId(), e.getRootCause(), seed);
        } finally {
            // The order has completed processing, remove the order from the processing map
            removeLock(order.getId());
        }
    }

    /**
     * Checks if the <b>order</b> has already been gone through the checkout workflow.
     */
    private boolean hasOrderBeenCompleted(Order order) {
        return (OrderStatus.SUBMITTED.equals(order.getOrderStatus()) ||
                OrderStatus.CANCELLED.equals(order.getOrderStatus()));
    }

    /**
     * Get an object to lock on for the given order id
     *
     * @param orderId order id
     * @return null if there was not already a lock object available. If an object was already
     * in the map, this will return that object, which means that there is already
     * a thread attempting to go through the checkout workflow
     */
    private Object putLock(Long orderId) {
        return lockMap.putIfAbsent(orderId, new Object());
    }

    /**
     * Done with processing the given orderId, remove the lock from the map
     *
     * @param orderId order id
     */
    private void removeLock(Long orderId) {
        lockMap.remove(orderId);
    }
}