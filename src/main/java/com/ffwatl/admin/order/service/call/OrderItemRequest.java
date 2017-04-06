package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.i18n.domain.I18n;

/**
 * @author ffw_ATL.
 */
public class OrderItemRequest extends AbstractOrderItemRequest {

    private I18n itemName;
    private ProductAttribute productAttribute;
    private Color color;

    public OrderItemRequest() {
        super();
    }

    public OrderItemRequest(AbstractOrderItemRequest request) {
        setQuantity(request.getQuantity());
        setOrder(request.getOrder());
        setSalePriceOverride(request.getSalePriceOverride());
        setRetailPriceOverride(request.getRetailPriceOverride());
    }

    @Override
    public OrderItemRequest clone() {
        OrderItemRequest returnRequest = new OrderItemRequest();
        copyProperties(returnRequest);
        returnRequest.setItemName(itemName);
        return returnRequest;
    }

    public I18n getItemName() {
        return itemName;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public Color getColor() {
        return color;
    }

    public void setItemName(I18n itemName) {
        this.itemName = itemName;
    }

    public void setProductAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "itemName=" + itemName +
                ", productAttribute=" + productAttribute +
                ", color=" + color +
                '}' + super.toString();
    }
}