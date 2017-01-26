package com.ffwatl.admin.order.domain;


import java.io.Serializable;

/**
 * Domain object used to synchronize {@link Order} operations.
 */
public interface OrderLock extends Serializable {

    long getId();

    boolean istLocked();

    long getLastUpdated();

    /**
     * @return the key used to identify the creator of the lock
     */
    String getKey();


    OrderLock setId(long id);

    OrderLock setLocked(boolean locked);

    /**
     * Set the time of alteration
     */
    OrderLock setLastUpdated(long lastUpdated);

    /**
     * Set a key identifying the creator of the lock.
     */
    OrderLock setKey(String key);
}
