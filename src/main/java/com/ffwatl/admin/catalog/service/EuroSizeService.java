package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.catalog.domain.ProductAttributeTypeImpl;

import java.util.List;

public interface EuroSizeService {

    ProductAttributeTypeImpl findById(long id);

    void save(ProductAttributeType eu);

    void save(List<ProductAttributeType> list);

    void removeSizeById(long id);

    List<ProductAttributeType> findAll();

    List<ProductAttributeType> findByCat(CommonCategory cat);

    List<ProductAttributeType> findAllUsed();

}
