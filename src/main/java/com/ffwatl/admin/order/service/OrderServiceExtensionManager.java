package com.ffwatl.admin.order.service;

import com.ffwatl.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * @author ffw_ATL.
 */
@Service("order_service_extension_manager")
public class OrderServiceExtensionManager extends ExtensionManager<OrderServiceExtensionHandler> {


    /**
     * Should take in a className that matches the ExtensionHandler interface being managed.
     */
    public OrderServiceExtensionManager() {
        super(OrderServiceExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
