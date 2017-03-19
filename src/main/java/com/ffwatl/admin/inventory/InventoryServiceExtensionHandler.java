package com.ffwatl.admin.inventory;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.order.service.workflow.CheckAvailabilityActivity;
import com.ffwatl.common.extension.ExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultHolder;
import com.ffwatl.common.extension.ExtensionResultStatusType;

import java.util.Collection;
import java.util.Map;

/**
 * Marker interface to dictate the overridden methods within {@link ContextualInventoryService}. Usually, implementers
 * will want to only override the {@link ContextualInventoryService} methods rather than all of the methods included
 * in {@link InventoryService} and so you will extend from {@link AbstractInventoryServiceExtensionHandler}.
 *
 * @author ffw_ATL.
 */
public interface InventoryServiceExtensionHandler extends ExtensionHandler {

    /**
     * Usually invoked within the {@link CheckAvailabilityActivity} to retrieve the quantity
     * that is available for the given <b>attr</b>
     */
    ExtensionResultStatusType retrieveQuantitiesAvailable(Collection<ProductAttribute> attr,
                                                          Map<String, Object> context,
                                                          ExtensionResultHolder<Map<ProductAttribute, Integer>> result);

    ExtensionResultStatusType decrementInventory(Map<ProductAttribute, Integer> attrQuantities,
                                                 Map<String, Object> context) throws InventoryUnavailableException;

    ExtensionResultStatusType incrementInventory(Map<ProductAttribute, Integer> attrQuantities,
                                                 Map<String, Object> context);
}
