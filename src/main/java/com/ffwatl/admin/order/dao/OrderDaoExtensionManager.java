package com.ffwatl.admin.order.dao;


import com.ffwatl.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

@Service("order_dao_extension_manager")
public class OrderDaoExtensionManager extends ExtensionManager<OrderDaoExtensionHandler> {

    /**
     * Should take in a className that matches the ExtensionHandler interface being managed.
     */
    public OrderDaoExtensionManager() {
        super(OrderDaoExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}