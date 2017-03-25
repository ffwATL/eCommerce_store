package com.ffwatl.admin.catalog.domain;

import java.util.List;


public interface ProductAttribute extends Comparable<ProductAttribute> {

    long getId();

    Product getProduct();

    ProductAttributeType getEu_size();

    int getQuantity();

    List<Field> getFields();

    int getVersion();

    ProductAttribute setId(long id);

    ProductAttribute setProduct(Product product);

    ProductAttribute setQuantity(int quantity);

    ProductAttribute setFields(List<Field> measurements);

    ProductAttribute setEu_size(ProductAttributeType eu_size);

    ProductAttribute setVersion(int version);
}