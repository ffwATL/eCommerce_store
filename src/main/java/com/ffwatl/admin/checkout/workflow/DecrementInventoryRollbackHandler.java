package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.inventory.InventoryService;
import com.ffwatl.admin.inventory.InventoryUnavailableException;
import com.ffwatl.admin.workflow.Activity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.state.RollbackFailureException;
import com.ffwatl.admin.workflow.state.RollbackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Decrements inventory that was put on by the {@link DecrementInventoryActivity}
 *
 * @author ffw_ATL.
 */
@Component("decrement_inventory_rollback_handler")
public class DecrementInventoryRollbackHandler implements RollbackHandler<CheckoutSeed> {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ROLLBACK_INVENTORY_DECREMENTED = "ROLLBACK_INVENTORY_DECREMENTED";
    public static final String ROLLBACK_INVENTORY_INCREMENTED = "ROLLBACK_INVENTORY_INCREMENTED";
    public static final String ROLLBACK_ORDER_ID = "ROLLBACK_ORDER_ID";
    public static final String EXTENDED_ROLLBACK_STATE = "EXTENDED_ROLLBACK_STATE";

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;


    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity,
                              ProcessContext<CheckoutSeed> processContext,
                              Map<String, Object> stateConfiguration) throws RollbackFailureException {
        if(!shouldExecute(stateConfiguration)){
            return;
        }

        String orderId = "(Not Known)";
        if (stateConfiguration.get(ROLLBACK_ORDER_ID) != null) {
            orderId = String.valueOf(stateConfiguration.get(ROLLBACK_ORDER_ID));
        }

        @SuppressWarnings("unchecked")
        Map<ProductAttribute, Integer> inventoryToIncrement = (Map<ProductAttribute, Integer>) stateConfiguration.get(ROLLBACK_INVENTORY_DECREMENTED);
        @SuppressWarnings("unchecked")
        Map<ProductAttribute, Integer> inventoryToDecrement = (Map<ProductAttribute, Integer>) stateConfiguration.get(ROLLBACK_INVENTORY_INCREMENTED);


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
                stateConfiguration.get(ROLLBACK_INVENTORY_DECREMENTED) != null ||
                        stateConfiguration.get(ROLLBACK_INVENTORY_INCREMENTED) != null ||
                        stateConfiguration.get(EXTENDED_ROLLBACK_STATE) != null
        );
    }
}