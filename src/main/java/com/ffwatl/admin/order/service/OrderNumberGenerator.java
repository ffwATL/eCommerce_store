package com.ffwatl.admin.order.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ffw_ATL.
 */
public class OrderNumberGenerator {

    private AtomicInteger atomicInteger;

    public OrderNumberGenerator(int initialValue){
        if(initialValue == 0) {
            initialValue = 1000;
        }
        this.atomicInteger = new AtomicInteger(initialValue);
    }

    public int getCounter() {
        return atomicInteger.getAndIncrement();
    }

}