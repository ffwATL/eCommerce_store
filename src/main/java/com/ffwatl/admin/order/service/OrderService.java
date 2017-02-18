package com.ffwatl.admin.order.service;


import com.ffwatl.admin.order.domain.Order;

public interface OrderService {

    /**
     * Persists the given order to the database. If the priceOrder flag is set to true,
     * the pricing workflow will execute before the order is written to the database.
     * Generally, you will want to price the order in every request scope once, and
     * preferably on the last call to save() for performance reasons.
     *
     * However, if you have logic that depends on the Order being priced, there are no
     * issues with saving as many times as necessary.
     *
     * @param order
     * @param priceOrder
     * @return the persisted Order, which will be a different instance than the Order passed in
     * @throws PricingException
     */
    Order save(Order order, Boolean priceOrder) /*throws PricingException*/;
}
