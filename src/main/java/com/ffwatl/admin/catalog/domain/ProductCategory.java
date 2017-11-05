package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;

import java.util.List;

public interface ProductCategory extends Comparable<ProductCategory>{

    boolean FETCHED = true;
    boolean NO_CHILD = false;

    long getId();

    int getLevel();

    int getWeight();

    User getCreatedBy();

    List<ProductCategory> getChild();

    I18n getGroupName();

    String getDescription();

    ProductAttributeTemplate getProductAttributeTemplate();

    ProductCategory setId(long id);

    ProductCategory setGroupName(I18n groupName);

    ProductCategory setCreatedBy(User createdBy);

    ProductCategory setDescription(String description);

    ProductCategory setChild(List<ProductCategory> child);

    ProductCategory setLevel(int level);

    ProductCategory setWeight(int weight);

    ProductCategory setProductAttributeTemplate(ProductAttributeTemplate attributeTemplate);

}