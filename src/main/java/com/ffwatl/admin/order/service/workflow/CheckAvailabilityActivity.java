package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.exception.ProductAttributeIsNotAvailableException;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.workflow.BaseActivity;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.common.persistence.FetchMode;

import javax.annotation.Resource;


/**
 * This activity handles both adds and updates. In both cases, this will check the availability
 * and quantities (if applicable) of the passed in request.
 *
 * @author ffw_ATL.
 */
public class CheckAvailabilityActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        long productId = request.getItemRequest().getProductId();
        ProductAttribute attribute = request.getItemRequest().getProductAttribute();
        long attributeId = attribute.getId();
        int requestedQty = attribute.getQuantity();
        int availableQty = 0;

        attribute = catalogService.findProductAttributeById(attributeId, FetchMode.LAZY);

        if(attribute != null){
            availableQty = attribute.getQuantity();
        }

        if(requestedQty > availableQty){
            throw new ProductAttributeIsNotAvailableException("Requested quantity is not available for this attribute. ",
                    productId, attributeId, requestedQty, availableQty);
        }

        return context;
    }
}