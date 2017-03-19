package com.ffwatl.admin.catalog.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.*;

import java.util.List;



public class ProductAttributeDTO implements ProductAttribute {

    private long id;

    @JsonDeserialize(contentAs = ProductImpl.class)
    private Product product;

    private int quantity;

    @JsonDeserialize(contentAs=FieldDTO.class)
    private List<Field> measurements;

    @JsonDeserialize(as=ProductAttributeTypeDTO.class)
    private ProductAttributeType eu_size;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public ProductAttributeType getEu_size() {
        return eu_size;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public List<Field> getFields() {
        return measurements;
    }

    @Override
    public ProductAttribute setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductAttribute setProduct(Product product) {
        this.product = product;
        return this;
    }

    @Override
    public ProductAttribute setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ProductAttribute setFields(List<Field> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Override
    public ProductAttribute setEu_size(ProductAttributeType eu_size) {
        this.eu_size = eu_size;
        return this;
    }

    @Override
    public int compareTo(ProductAttribute o) {
        if(o == null) return 1;
        return this.eu_size.compareTo(o.getEu_size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributeDTO that = (ProductAttributeDTO) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (measurements != null ? !measurements.equals(that.measurements) : that.measurements != null) return false;
        return !(getEu_size() != null ? !getEu_size().equals(that.getEu_size()) : that.getEu_size() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getQuantity();
        result = 31 * result + (measurements != null ? measurements.hashCode() : 0);
        result = 31 * result + (getEu_size() != null ? getEu_size().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                ", eu_size=" + eu_size +
                '}';
    }
}
