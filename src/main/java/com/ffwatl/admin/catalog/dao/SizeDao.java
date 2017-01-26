package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductAttribute;

public interface SizeDao {

    ProductAttribute findById(long id);
    void removeById(long id);

}