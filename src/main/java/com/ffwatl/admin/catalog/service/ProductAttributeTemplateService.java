package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

/**
 * @author mmed 11/17/17
 */
public interface ProductAttributeTemplateService {

    ProductAttributeTemplate findById(long id, FetchMode fetchMode);

    List<ProductAttributeTemplate> findAll(FetchMode fetchMode);

    void save(ProductAttributeTemplate attributeTemplate);

    void removeById(long id);

    void remove(ProductAttributeTemplate attributeTemplate);
}
