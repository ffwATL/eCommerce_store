package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;


public interface ProductAttributeType extends Comparable<ProductAttributeType> {

    long getId();

    CommonCategory getCat();

    I18n getName();

    ProductAttributeType setId(long id);

    ProductAttributeType setCat(CommonCategory cat);

    ProductAttributeType setName(I18n name);
}