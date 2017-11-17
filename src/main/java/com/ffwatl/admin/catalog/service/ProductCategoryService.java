package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findById(long id);

    void save(ProductCategory itemGroup);

    void save(List<? extends ProductCategory> list);

    ProductCategory findByName(String name, FetchMode fetchMode);

    ProductCategory findByLvlAndByName(int lvl, String name, FetchMode fetchMode);

    List<ProductCategory> findByLevel(int lvl, FetchMode fetchMode);

    List<ProductCategory> findAllUsed(FetchMode fetchMode);
}