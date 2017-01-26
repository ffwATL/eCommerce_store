package com.ffwatl.admin.catalog.domain;

import java.util.List;


public interface ProductAttribute extends Comparable<ProductAttribute> {
    long getId();

    ProductAttributeType getEu_size();

    int getQuantity();

    List<Field> getFields();

    ProductAttribute setId(long id);

    ProductAttribute setQuantity(int quantity);

    ProductAttribute setFields(List<Field> measurements);

    ProductAttribute setEu_size(ProductAttributeType eu_size);
}