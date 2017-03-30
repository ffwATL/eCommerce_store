package com.ffwatl.admin.order.service.workflow.cancel;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.inventory.InventoryService;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class IncrementInventoryCancelOrderRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "inventory_service")
    private InventoryService inventoryService;


    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {

        CartOperationRequest request = context.getSeedData();
        List<OrderItem> orderItems = request.getOrder().getOrderItems();

        // It means we have a wishlist and we need to remove orderItem without processing inventory
        if(request.getOrder().getOrderStatus() == OrderStatus.NAMED){
            return context;
        }

        // map to hold attributes and quantity purchased
        HashMap<ProductAttribute, Integer> attrInventoryMap = new HashMap<>();

        for(OrderItem orderItem: orderItems){
            attrInventoryMap.put(orderItem.getProductAttribute(), orderItem.getQuantity());
        }

        inventoryService.incrementInventory(attrInventoryMap);

        return context;
    }
}
