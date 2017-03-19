package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.common.persistence.FetchMode;

public interface ProductAttributeDao {

    ProductAttribute findById(long id);
    ProductAttribute findById(long id, FetchMode fetchMode);
    void removeById(long id);

}