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

    ProductCategory getParent();

    List<ProductCategory> getChild();

    I18n getCategoryName();

    String getDescription();

    ProductAttributeTemplate getProductAttributeTemplate();


    ProductCategory setId(long id);

    ProductCategory setLevel(int level);

    ProductCategory setWeight(int weight);

    ProductCategory setCreatedBy(User createdBy);

    ProductCategory setParent(ProductCategory parent);

    ProductCategory setChild(List<ProductCategory> child);

    ProductCategory setCategoryName(I18n categoryName);

    ProductCategory setDescription(String description);

    ProductCategory setProductAttributeTemplate(ProductAttributeTemplate attributeTemplate);

}