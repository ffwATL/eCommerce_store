package com.ffwatl.admin.catalog.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.catalog.domain.Field;
import com.ffwatl.admin.catalog.domain.ProductAttribute;

import java.util.List;



public class ProductAttributeDTO implements ProductAttribute {

    private long id;
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
    public String toString() {
        return "ProductAttributeDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                ", eu_size=" + eu_size +
                '}';
    }
}
