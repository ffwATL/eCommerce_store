package com.ffwatl.admin.inventory;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.checkout.workflow.DecrementInventoryCheckoutActivity;
import com.ffwatl.admin.checkout.workflow.DecrementInventoryRollbackHandler;

import java.util.Map;

/**
 * @author ffw_ATL.
 */
public interface InventoryService {

    /**
     * Used as a key in the context map methods below. This is used for the current order that should be used to evaluate
     * the methods below
     */
    String ORDER_KEY = "ORDER";

    /**
     * Used as a key in the context map methods below. This key is normally populated from the {@link DecrementInventoryCheckoutActivity}
     * and is utilized in the {@link DecrementInventoryRollbackHandler}. This can be cast to a Map<String, Object> and is
     * designed such that when it is used, non-read operations (decrement and increment) can add what actually happened
     * so that it can be reversed.
     */
    String ROLLBACK_STATE_KEY = "ROLLBACK_STATE";

    int retrieveQuantityAvailable(ProductAttribute attribute);

    boolean isAvailable(ProductAttribute attribute, int quantity);

    boolean checkBasicAvailablility(ProductAttribute attribute);

    void decrementInventory(ProductAttribute attribute, int quantity) throws InventoryUnavailableException;

    void decrementInventory(Map<ProductAttribute, Integer> attrQuantities) throws InventoryUnavailableException;

    void incrementInventory(ProductAttribute attribute, int quantity) throws InventoryUnavailableException;

    void incrementInventory(Map<ProductAttribute, Integer> attrQuantities) throws InventoryUnavailableException;
}