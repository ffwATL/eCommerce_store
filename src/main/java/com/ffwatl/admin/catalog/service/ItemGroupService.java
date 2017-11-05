package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.CommonCategory;

import java.util.List;

public interface ItemGroupService {

    ProductCategory findById(long id);

    void save(ProductCategory itemGroup);

    void save(List<? extends ProductCategory> list);

    ProductCategory findByName(String name);

    ProductCategory findByLvlAndByNameFetchCollection(int lvl, String name);

    List<ProductCategory> findByCatNoChildren(CommonCategory cat);

    List<ProductCategory> findByLvlLazyWithoutChild(int lvl);

    List<ProductCategory> findByLvlEager(int lvl);

    List<ProductCategory> findAllUsed();

    List<ProductCategory> findGenderGroup();

}