package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.order.domain.Order;

/**
 *  Only the product is required to add an item to an order.
 *
 * The category can be inferred from the product's default category.
 *
 * The sku can be inferred from either the passed in attributes as they are compared to the product's options or
 * the sku can be determined from the product's default sku.
 *
 * Personal message is optional.
 *
 * @author ffw_ATL.
 */
public abstract class AbstractOrderItemRequest {

    protected String sku;
    protected Category category;
    protected Product product;
    protected Order order;
    protected int quantity;
    protected int salePriceOverride;
    protected int retailPriceOverride;
    private boolean incrementOrderItemQuantity;

    public String getSku() {
        return sku;
    }

    public boolean isIncrementOrderItemQuantity() {
        return incrementOrderItemQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSalePriceOverride() {
        return salePriceOverride;
    }

    public int getRetailPriceOverride() {
        return retailPriceOverride;
    }



    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setIncrementOrderItemQuantity(boolean incrementOrderItemQuantity) {
        this.incrementOrderItemQuantity = incrementOrderItemQuantity;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSalePriceOverride(int salePriceOverride) {
        this.salePriceOverride = salePriceOverride;
    }

    public void setRetailPriceOverride(int retailPriceOverride) {
        this.retailPriceOverride = retailPriceOverride;
    }

    protected void copyProperties(AbstractOrderItemRequest newRequest) {
        newRequest.setCategory(category);
        newRequest.setProduct(product);
        newRequest.setQuantity(quantity);
        newRequest.setSku(sku);
        newRequest.setOrder(order);
        newRequest.setSalePriceOverride(salePriceOverride);
        newRequest.setRetailPriceOverride(retailPriceOverride);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractOrderItemRequest that = (AbstractOrderItemRequest) o;

        if (incrementOrderItemQuantity != that.isIncrementOrderItemQuantity()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getSalePriceOverride() != that.getSalePriceOverride()) return false;
        if (getRetailPriceOverride() != that.getRetailPriceOverride()) return false;
        if (getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null)
            return false;
        if (getProduct() != null ? !getProduct().equals(that.getProduct()) : that.getProduct() != null) return false;
        return !(getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null);

    }

    @Override
    public int hashCode() {
        int result = getCategory() != null ? getCategory().hashCode() : 0;
        result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + getQuantity();
        result = 31 * result + getSalePriceOverride();
        result = 31 * result + getRetailPriceOverride();
        result = 31 * result + (isIncrementOrderItemQuantity() ? 1 : 0);
        return result;
    }
}