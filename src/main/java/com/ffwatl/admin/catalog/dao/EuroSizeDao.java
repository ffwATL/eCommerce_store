package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeTypeImpl;

import java.util.List;

public interface EuroSizeDao {

    List<ProductAttributeTypeImpl> findByCat(CommonCategory cat);

    ProductAttributeTypeImpl findById(long id);

    void save(ProductAttributeTypeImpl eu);

    void removeSizeById(long id);

    List<ProductAttributeTypeImpl> findAll();

    List<ProductAttributeTypeImpl> findAllUsed();

}