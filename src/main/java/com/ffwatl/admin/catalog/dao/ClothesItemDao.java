package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductClothes;

import java.util.List;

public interface ClothesItemDao {

    ProductClothes findById(long id);

    List<ProductClothes> findAll();

    void save(ProductClothes item);

    void remove(ProductClothes item);
}