package com.ffwatl.admin.catalog.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.*;

import java.util.List;

//FIXME: Add DTO classes, otherwise it will fails on serialization attempt

public class ProductAttributeDTO implements ProductAttribute {

    private long id;

    @JsonDeserialize(contentAs = ProductImpl.class)
    private Product product;

    private int quantity;

    private AttributeName attributeName;

    @JsonDeserialize(contentAs=FieldDTO.class)
    private List<Field> fields;

    @JsonDeserialize(as=ProductAttributeTypeDTO.class)
    private ProductAttributeType attributeType;




    @Override
    public long getId() {
        return id;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public AttributeName getAttributeName() {
        return attributeName;
    }

    @Override
    public ProductAttributeType getProductAttributeType() {
        return attributeType;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public int getVersion() {
        return 0;
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
    public ProductAttribute setAttributeName(AttributeName name) {
        this.attributeName = name;
        return this;
    }
    @Override
    public ProductAttribute setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ProductAttribute setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public ProductAttribute setProductAttributeType(ProductAttributeType attributeType) {
        this.attributeType = attributeType;
        return this;
    }

    @Override
    public ProductAttribute setVersion(int version) {
        return null;
    }

    @Override
    public int compareTo(ProductAttribute o) {
        if(o == null) return 1;
        return this.attributeType.compareTo(o.getProductAttributeType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributeDTO that = (ProductAttributeDTO) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
        return !(getProductAttributeType() != null ? !getProductAttributeType().equals(that.getProductAttributeType()) : that.getProductAttributeType() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getQuantity();
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        result = 31 * result + (getProductAttributeType() != null ? getProductAttributeType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", fields=" + fields +
                ", attributeType=" + attributeType +
                '}';
    }
}
