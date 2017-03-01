package com.ffwatl.admin.order.service.workflow.add;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.persistence.FetchMode;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
public class ValidateAddRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "catalog_service")
    private CatalogService catalogService;


    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Quantity was not specified or was equal to zero. We will not throw an exception,
        // but we will preven the workflow from continuing to execute
        if (orderItemRequestDTO.getQuantity() == 0) {
            context.stopProcess();
            return context;
        }

        // Throw an exception if the user tried to add a negative quantity of something
        if (orderItemRequestDTO.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        // Throw an exception if the user did not specify an order to add the item to
        if (request.getOrder() == null) {
            throw new IllegalArgumentException("Order is required when adding item to order");
        }

        // Validate that if the user specified a productId, it is a legitimate productId
        Product product;
        if (orderItemRequestDTO.getProductId() > 0) {
            product = catalogService.findProductById(orderItemRequestDTO.getProductId(), FetchMode.LAZY);
            if (product == null) {
                throw new IllegalArgumentException("Product was specified but no matching product was found for productId " + orderItemRequestDTO.getProductId());
            }
        }
        return context;
    }
}