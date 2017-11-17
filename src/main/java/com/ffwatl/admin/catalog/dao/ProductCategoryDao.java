package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.ProductCategoryImpl;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface ProductCategoryDao {

    ProductCategoryImpl findById(long id);

    ProductCategory findById(long id, FetchMode fetchMode);

    List<ProductCategory> findByName(String name, FetchMode fetchMode);

    ProductCategory save(ProductCategory itemGroup);

    void remove(ProductCategory itemGroup);

    List<ProductCategoryImpl> findByLevelAndByName(int level, String name);

    List<ProductCategoryImpl> findByLevel(int level);

    List<ProductCategory> findByLevel(int level, FetchMode fetchMode);

    List<ProductCategory> findAllInUse(FetchMode fetchMode);

    List<ProductCategory> findByLevelAndName(int level, String name, FetchMode fetchMode);

    List<ProductCategoryImpl> findByLvlFetchedChild(int lvl);
}