package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.inventory.InventoryRollback;
import com.ffwatl.admin.inventory.InventoryService;
import com.ffwatl.admin.inventory.InventoryUnavailableException;
import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Component("update_inventory_rollback_handler")
@Scope("prototype")
public class UpdateInventoryRollbackHandler implements RollbackHandler<CartOperationRequest> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;

    @Override
    public void rollbackState(Activity<? extends ProcessContext<CartOperationRequest>> activity,
                              ProcessContext<CartOperationRequest> processContext,
                              Map<String, Object> stateConfiguration) throws RollbackFailureException {

        if(!shouldExecute(stateConfiguration)){
            return;
        }

        String orderId = "(Not Known)";
        if (stateConfiguration.get(InventoryRollback.ROLLBACK_ORDER_ID) != null) {
            orderId = String.valueOf(stateConfiguration.get(InventoryRollback.ROLLBACK_ORDER_ID));
        }

        @SuppressWarnings("unchecked")
        Map<ProductAttribute, Integer> inventoryToIncrement = (Map<ProductAttribute, Integer>) stateConfiguration.get(InventoryRollback.ROLLBACK_INVENTORY_DECREMENTED);
        @SuppressWarnings("unchecked")
        Map<ProductAttribute, Integer> inventoryToDecrement = (Map<ProductAttribute, Integer>) stateConfiguration.get(InventoryRollback.ROLLBACK_INVENTORY_INCREMENTED);


        if (inventoryToIncrement != null && !inventoryToIncrement.isEmpty()) {
            try {
                inventoryService.incrementInventory(inventoryToIncrement);
            } catch (Exception ex) {
                RollbackFailureException rfe = new RollbackFailureException("An unexpected error occured in the error handler of the checkout workflow trying to compensate for inventory. This happend for order ID: " +
                        orderId + ". This should be corrected manually!", ex);
                rfe.setActivity(activity);
                rfe.setProcessContext(processContext);
                rfe.setStateItems(stateConfiguration);
                throw rfe;
            }
        }

        if (inventoryToDecrement != null && !inventoryToDecrement.isEmpty()) {
            try {
                inventoryService.decrementInventory(inventoryToDecrement);
            } catch (InventoryUnavailableException e) {
                //This is an awkward, unlikely state.  I just added some inventory, but something happened, and I want to remove it, but it's already gone!
                RollbackFailureException rfe = new RollbackFailureException("While trying roll back (decrement) inventory, we found that there was none left decrement.", e);
                rfe.setActivity(activity);
                rfe.setProcessContext(processContext);
                rfe.setStateItems(stateConfiguration);
                throw rfe;
            } catch (RuntimeException ex) {
                LOGGER.error("An unexpected error occured in the error handler of the checkout workflow trying to compensate for inventory. This happend for order ID: " +
                        orderId + ". This should be corrected manually!", ex);
                RollbackFailureException rfe = new RollbackFailureException("An unexpected error occured in the error handler of the checkout workflow " +
                        "trying to compensate for inventory. This happend for order ID: " +
                        orderId + ". This should be corrected manually!", ex);
                rfe.setActivity(activity);
                rfe.setProcessContext(processContext);
                rfe.setStateItems(stateConfiguration);
                throw rfe;
            }
        }

    }

    /**
     * Returns true if this rollback handler should execute
     */
    private boolean shouldExecute(Map<String, Object> stateConfiguration) {
        return stateConfiguration != null && (
                stateConfiguration.get(InventoryRollback.ROLLBACK_INVENTORY_DECREMENTED) != null ||
                        stateConfiguration.get(InventoryRollback.ROLLBACK_INVENTORY_INCREMENTED) != null ||
                        stateConfiguration.get(InventoryRollback.EXTENDED_ROLLBACK_STATE) != null
        );
    }
}