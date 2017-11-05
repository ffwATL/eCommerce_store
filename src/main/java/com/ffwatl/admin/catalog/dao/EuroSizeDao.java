package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;

import java.util.List;

public interface EuroSizeDao {

    List<ProductAttributeType> findByCat(CommonCategory cat);

    ProductAttributeType findById(long id);

    void save(ProductAttributeType eu);

    void removeSizeById(long id);

    List<ProductAttributeType> findAll();

    List<ProductAttributeType> findAllUsed();

}