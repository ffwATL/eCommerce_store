package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface ProductDao {

    ProductImpl findById(long id);
    Product findById(long id, FetchMode fetchMode);
    List<ProductImpl> findAll();
    List<Product> findAll(FetchMode fetchMode);
    Product save(Product item);
    void remove(Product item);

    List<Product> findByStatus(boolean isActive, FetchMode fetchMode);
}
