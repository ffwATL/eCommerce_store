package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Only the product and quantity are required to add an item to an order.
 *
 * The category can be inferred from the product's default category.
 *
 * The sku can be inferred from either the passed in attributes as they are compared to the product's options or
 * the sku can be determined from the product's default sku.
 *
 *
 * Important Note:  To protect against misuse, the {@link OrderService}'s addItemToCart method will blank out
 * any values passed in on this DTO for the overrideSalePrice or overrideRetailPrice.
 *
 * Instead, implementors should call the more explicit addItemWithPriceOverrides.
 *
 * @author ffw_ATL.
 */
public class OrderItemRequestDTO {

    private String sku;
    private long categoryId;
    private I18n itemName;
    private long productId;
    private long orderItemId;
    private int quantity;
    private int overrideSalePrice;
    private int overrideRetailPrice;
    private ProductAttribute productAttribute;
    private List<OrderItemRequestDTO> childOrderItems = new ArrayList<>();
    private long parentOrderItemId;

    public OrderItemRequestDTO() {}

    public OrderItemRequestDTO(long productId, int quantity) {
        this(productId, quantity, null);
    }

    public OrderItemRequestDTO(long productId, int quantity, String sku) {
        this(productId, quantity, sku, 0);
    }

    public OrderItemRequestDTO(long productId, int quantity, String sku, long categoryId) {
        setProductId(productId);
        setSku(sku);
        setCategoryId(categoryId);
        setQuantity(quantity);
    }

    public OrderItemRequestDTO(OrderItemRequest request){
        this(request.getProduct().getId(), request.getQuantity(), request.getSku(), request.getCategory().getId());
        this.productAttribute = request.getProductAttribute();
        this.setProductName(request.getItemName());
    }

    public String getSku() {
        return sku;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public I18n getItemName() {
        return itemName;
    }

    public long getProductId() {
        return productId;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOverrideSalePrice() {
        return overrideSalePrice;
    }

    public int getOverrideRetailPrice() {
        return overrideRetailPrice;
    }

    public ProductAttribute getProductAttribute() {
        return productAttribute;
    }

    public List<OrderItemRequestDTO> getChildOrderItems() {
        return childOrderItems;
    }

    public long getParentOrderItemId() {
        return parentOrderItemId;
    }



    public OrderItemRequestDTO setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public OrderItemRequestDTO setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public OrderItemRequestDTO setProductName(I18n itemName) {
        this.itemName = itemName;
        return this;
    }

    public OrderItemRequestDTO setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public OrderItemRequestDTO setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
        return this;
    }

    public OrderItemRequestDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItemRequestDTO setOverrideSalePrice(int overrideSalePrice) {
        this.overrideSalePrice = overrideSalePrice;
        return this;
    }

    public OrderItemRequestDTO setOverrideRetailPrice(int overrideRetailPrice) {
        this.overrideRetailPrice = overrideRetailPrice;
        return this;
    }

    public OrderItemRequestDTO setItemAttribute(ProductAttribute productAttribute) {
        this.productAttribute = productAttribute;
        return this;
    }

    public OrderItemRequestDTO setChildOrderItems(List<OrderItemRequestDTO> childOrderItems) {
        this.childOrderItems = childOrderItems;
        return this;
    }

    public OrderItemRequestDTO setParentOrderItemId(long parentOrderItemId) {
        this.parentOrderItemId = parentOrderItemId;
        return this;
    }

    @Override
    public String toString() {
        return "OrderItemRequestDTO{" +
                "sku='" + sku + '\'' +
                ", categoryId=" + categoryId +
                ", itemName=" + itemName +
                ", productId=" + productId +
                ", orderItemId=" + orderItemId +
                ", quantity=" + quantity +
                ", overrideSalePrice=" + overrideSalePrice +
                ", overrideRetailPrice=" + overrideRetailPrice +
                ", productAttribute=" + productAttribute +
                ", childOrderItems=" + childOrderItems +
                ", parentOrderItemId=" + parentOrderItemId +
                '}';
    }
}