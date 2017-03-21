package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.inventory.InventoryRollback;
import com.ffwatl.admin.inventory.InventoryService;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.ActivityStateManagerImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Decrements inventory
 *
 * @author ffw_ATL.
 */
public class DecrementInventoryCheckoutActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;

    public DecrementInventoryCheckoutActivity() {
        super();
        super.setAutomaticallyRegisterRollbackHandler(false);
    }

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        CheckoutSeed seed = context.getSeedData();
        List<OrderItem> orderItems = seed.getOrder().getOrderItems();

        //map to hold attributes and quantity purchased
        HashMap<ProductAttribute, Integer> attrInventoryMap = new HashMap<>();

        for (OrderItem orderItem : orderItems) {
            ProductAttribute attr = orderItem.getProductAttribute();
            Integer quantity = attrInventoryMap.get(attr);

            if (quantity == null) {
                quantity = orderItem.getQuantity();
            } else {
                quantity += orderItem.getQuantity();
            }

            attrInventoryMap.put(attr, quantity);
        }

        Map<String, Object> rollbackState = new HashMap<>();
        if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
            if (getStateConfiguration() != null && !getStateConfiguration().isEmpty()) {
                rollbackState.putAll(getStateConfiguration());
            }
            // Register the map with the rollback state object early on; this allows the extension handlers to incrementally
            // add state while decrementing but still throw an exception
            ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackRegion(), getRollbackHandler(), rollbackState);
        }

        if (!attrInventoryMap.isEmpty()) {
            Map<String, Object> contextualInfo = new HashMap<>();
            contextualInfo.put(InventoryService.ORDER_KEY, context.getSeedData().getOrder());
            contextualInfo.put(InventoryService.ROLLBACK_STATE_KEY, new HashMap<String, Object>());

            inventoryService.decrementInventory(attrInventoryMap);

            if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
                rollbackState.put(InventoryRollback.ROLLBACK_INVENTORY_DECREMENTED, attrInventoryMap);
                rollbackState.put(InventoryRollback.ROLLBACK_ORDER_ID, seed.getOrder().getId());
            }

            // add the rollback state that was used in the rollback handler
            rollbackState.put(InventoryRollback.EXTENDED_ROLLBACK_STATE, contextualInfo.get(InventoryService.ROLLBACK_STATE_KEY));
        }

        return context;
    }
}
