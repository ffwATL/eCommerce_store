package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductAttribute;

public interface ProductAttributeService {

    ProductAttribute findById(long id);
    void removeById(long ... id);

}