package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplateImpl;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.persistence.FetchModeOption;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Collections;
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
        CriteriaProperty<ProductAttributeTemplate, ProductAttributeTemplateImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<ProductAttributeTemplate> criteria = property.getCriteria();
        Root<ProductAttributeTemplateImpl> root = property.getRoot();

        criteria.where(property.getBuilder().equal(root.get("id"), id));
        List<ProductAttributeTemplate> result = em.createQuery(criteria).getResultList();

        if (result ==null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public ProductAttributeTemplate findByTemplateName(String templateName, FetchMode fetchMode) {
        CriteriaProperty<ProductAttributeTemplate, ProductAttributeTemplateImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<ProductAttributeTemplate> criteria = property.getCriteria();
        Root<ProductAttributeTemplateImpl> root = property.getRoot();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.and(
                cb.or(
                        cb.equal(root.get("templateName").get("locale_en"), templateName),
                        cb.equal(root.get("templateName").get("locale_ru"), templateName),
                        cb.equal(root.get("templateName").get("locale_ua"), templateName)
                )
        ));
        List<ProductAttributeTemplate> result = em.createQuery(criteria).getResultList();

        if (result ==null || result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<ProductAttributeTemplate> findAll(FetchMode fetchMode) {
        CriteriaProperty<ProductAttributeTemplate, ProductAttributeTemplateImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<ProductAttributeTemplate> criteria = property.getCriteria();

        List<ProductAttributeTemplate> result = em.createQuery(criteria).getResultList();
        return result != null ? result : Collections.emptyList();
    }

    @Override
    public List<ProductAttributeTemplate> findAllUsed(FetchMode fetchMode) {
        throw new NotImplementedException("Method is not implemented yet");
    }

    @Override
    public ProductAttributeTemplate save(ProductAttributeTemplate attributeTemplate) {
        return em.merge(attributeTemplate);
    }

    @Override
    public void remove(ProductAttributeTemplate attributeTemplate) {
        em.remove(attributeTemplate);
    }

    @Override
    public CriteriaProperty<ProductAttributeTemplate, ProductAttributeTemplateImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, ProductAttributeTemplate.class, ProductAttributeTemplateImpl.class);
    }

    @Override
    public void addFetch(Root<ProductAttributeTemplateImpl> root) {
        root.fetch("fields", JoinType.LEFT);
        root.fetch("attributeNames", JoinType.LEFT);
    }
}