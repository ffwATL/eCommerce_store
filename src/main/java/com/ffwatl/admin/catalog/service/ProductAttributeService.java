package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.common.persistence.FetchMode;

public interface ProductAttributeService {

    ProductAttribute findById(long id);

    ProductAttribute findById(long id, FetchMode fetchMode);

    void removeById(long ... id);

}