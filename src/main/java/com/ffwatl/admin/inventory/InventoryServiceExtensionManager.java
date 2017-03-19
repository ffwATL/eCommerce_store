package com.ffwatl.admin.inventory;

import com.ffwatl.common.extension.ExtensionManager;
import org.springframework.stereotype.Component;

/**
 * @author ffw_ATL.
 */
@Component("inventory_service_extension_manager")
public class InventoryServiceExtensionManager extends ExtensionManager<InventoryServiceExtensionHandler> {

    public InventoryServiceExtensionManager() {
        super(InventoryServiceExtensionHandler.class);
    }
}