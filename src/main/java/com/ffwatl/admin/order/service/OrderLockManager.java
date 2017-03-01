package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.Order;

/**
 * @author ffw_ATL.
 */
public interface OrderLockManager {

    /**
     * Blocks the requesting thread until able to acquire a lock for the given order.
     *
     * <b>NOTE</b>: Callers of this method MUST call {@link #releaseLock(Object)}, passing in the Object returned
     * from this call once their critical section has executed. The suggested idiom for this operation is:
     *
     * Object lockObject = null;
     * try {
     *     lockObject = orderLockManager.acquireLock(order);
     *     // Do something
     * } finally {
     *     orderLockManager.releaseLock(lockObject);
     * }
     *
     * @return the lock object, which should be passed in as the parameter to {@link #releaseLock(Object)} once
     *         the operation that required a lock has completed.
     */
    Object acquireLock(Order order);

    /**
     * This method differs from {@link #acquireLock(Order)} in that it will not block if the lock is currently
     * held by a different caller. In the case that the lock was not able to be immediately acquired, this method
     * will return null.
     *
     * @see #acquireLock(Order)
     *
     * @return the lock if it was immediately acquireable, null otherwise
     */
    Object acquireLockIfAvailable(Order order);

    /**
     * Releases the given lockObject and notifies any threads that are waiting on that object that they are able to
     * attempt to acquire the lock.
     */
    void releaseLock(Object lockObject);

    /**
     * This method indicates if the lock manager is active.  It can return a static value or a dynamic one
     * based on values in the RequestContext or other stateful mechanism.  A good example of when this might be
     * dynamic is when there is a session-based lock and the request indicates that it is not OK to use sessions.
     */
    boolean isActive();
}