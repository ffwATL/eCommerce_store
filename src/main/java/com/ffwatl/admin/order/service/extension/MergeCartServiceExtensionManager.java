package com.ffwatl.admin.order.service.extension;

import com.ffwatl.common.extension.ExtensionManager;
import org.springframework.stereotype.Service;

/**
 * @author ffw_ATL.
 */
@Service("merge_cart_service_extension_manager")
public class MergeCartServiceExtensionManager extends ExtensionManager<MergeCartServiceExtensionHandler>{

    /**
     * Should take in a className that matches the ExtensionHandler interface being managed.
     */
    public MergeCartServiceExtensionManager() {
        super(MergeCartServiceExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}