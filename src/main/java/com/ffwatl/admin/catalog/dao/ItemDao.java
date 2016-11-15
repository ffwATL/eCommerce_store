package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductDefault;

import java.util.List;

public interface ItemDao {

    ProductDefault findById(long id);
    List<ProductDefault> findAll();
    void save(ProductDefault item);
    void remove(ProductDefault item);

}
