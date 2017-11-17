package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

/**
 * @author mmed 11/17/17
 */
public interface ProductAttributeTemplateDao {

    ProductAttributeTemplate findById(long id, FetchMode fetchMode);

    ProductAttributeTemplate findByTemplateName(String templateName, FetchMode fetchMode);

    List<ProductAttributeTemplate> findAll(FetchMode fetchMode);

    List<ProductAttributeTemplate> findAllUsed(FetchMode fetchMode);

    ProductAttributeTemplate save(ProductAttributeTemplate attributeTemplate);

    void remove(ProductAttributeTemplate attributeTemplate);


}
