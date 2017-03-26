package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.inventory.InventoryRollback;
import com.ffwatl.admin.inventory.InventoryService;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.ActivityStateManagerImpl;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
public class UpdateInventoryActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        Integer requestedQuantity = orderItemRequestDTO.getQuantity();
        ProductAttribute attribute = orderItemRequestDTO.getProductAttribute();

        // map to hold attributes and quantity purchased
        HashMap<ProductAttribute, Integer> attrInventoryMap = new HashMap<>();
        attrInventoryMap.put(attribute, requestedQuantity);

        Map<String, Object> rollbackState = new HashMap<>();
        if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
            if (getStateConfiguration() != null && !getStateConfiguration().isEmpty()) {
                rollbackState.putAll(getStateConfiguration());
            }
            // Register the map with the rollback state object early on; this allows the extension handlers
            // to incrementally add state while decrementing but still throw an exception
            ActivityStateManagerImpl
                    .getStateManager()
                    .registerState(this, context, getRollbackRegion(), getRollbackHandler(), rollbackState);
        }

        if (!attrInventoryMap.isEmpty()) {
            Map<String, Object> contextualInfo = new HashMap<>();
            contextualInfo.put(InventoryService.ORDER_KEY, context.getSeedData().getOrder());
            contextualInfo.put(InventoryService.ROLLBACK_STATE_KEY, new HashMap<String, Object>());

            String rollbackInventoryKey = InventoryRollback.ROLLBACK_INVENTORY_DECREMENTED;

            if(orderItemRequestDTO.isIncrementOrderItemQuantity()) {
                // if we incremented orderItem quantity then we should decrement inventory
                inventoryService.decrementInventory(attribute, requestedQuantity);
            }else {
                //if we decremented quantity from orderItem then we should increment inventory
                inventoryService.incrementInventory(attribute, requestedQuantity);
                rollbackInventoryKey = InventoryRollback.ROLLBACK_INVENTORY_INCREMENTED;
            }

            if (getRollbackHandler() != null && !getAutomaticallyRegisterRollbackHandler()) {
                rollbackState.put(rollbackInventoryKey, attrInventoryMap);
                rollbackState.put(InventoryRollback.ROLLBACK_ORDER_ID, request.getOrder().getId());
            }

            // add the rollback state that was used in the rollback handler
            rollbackState.put(InventoryRollback.EXTENDED_ROLLBACK_STATE, contextualInfo.get(InventoryService.ROLLBACK_STATE_KEY));
        }

        return context;
    }
}