package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplateImpl;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.persistence.FetchModeOption;
import com.ffwatl.common.service.ConverterDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author mmed 11/17/17
 */
@Repository("product_attribute_template_dao")
public class ProductAttributeTemplateDaoImpl implements ProductAttributeTemplateDao, FetchModeOption<ProductAttributeTemplate, ProductAttributeTemplateImpl> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public ProductAttributeTemplate findById(long id, FetchMode fetchMode) {
        return null;
    }

    @Override
    public ProductAttributeTemplate findByTemplateName(String templateName, FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductAttributeTemplate> findAll(FetchMode fetchMode) {
        return null;
    }

    @Override
    public List<ProductAttributeTemplate> findAllUsed(FetchMode fetchMode) {
        return null;
    }

    @Override
    public ProductAttributeTemplate save(ProductAttributeTemplate attributeTemplate) {
        return null;
    }

    @Override
    public void remove(ProductAttributeTemplate attributeTemplate) {

    }

    @Override
    public CriteriaProperty<ProductAttributeTemplate, ProductAttributeTemplateImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return null;
    }

    @Override
    public void addFetch(Root<ProductAttributeTemplateImpl> root) {

    }
}