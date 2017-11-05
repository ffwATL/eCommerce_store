package com.ffwatl.admin.catalog.domain;

import java.util.List;


public interface ProductAttribute extends Comparable<ProductAttribute> {

    long getId();

    Product getProduct();

    AttributeName getAttributeName();

    ProductAttributeType getProductAttributeType();

    int getQuantity();

    List<Field> getFields();

    int getVersion();

    ProductAttribute setId(long id);

    ProductAttribute setProduct(Product product);

    ProductAttribute setAttributeName(AttributeName name);

    ProductAttribute setQuantity(int quantity);

    ProductAttribute setFields(List<Field> measurements);

    ProductAttribute setProductAttributeType(ProductAttributeType attributeType);

    ProductAttribute setVersion(int version);
}