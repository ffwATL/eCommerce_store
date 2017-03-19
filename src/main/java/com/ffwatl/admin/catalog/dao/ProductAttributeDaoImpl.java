package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductAttributeImpl;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductAttributeDaoImpl implements ProductAttributeDao,FetchModeOption<ProductAttribute, ProductAttributeImpl> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProductAttribute findById(long id) {
        return em.find(ProductAttributeImpl.class, id);
    }

    @Override
    public ProductAttribute findById(long id, FetchMode fetchMode) {
        CriteriaProperty<ProductAttribute, ProductAttributeImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<ProductAttribute> criteria = property.getCriteria();
        Root<ProductAttributeImpl> root = property.getRoot();

        criteria.where(property.getBuilder().equal(root.get("id"), id));
        List<ProductAttribute> result = em.createQuery(criteria).getResultList();

        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public void removeById(long id) {
        em.remove(findById(id));
    }

    @Override
    public CriteriaProperty<ProductAttribute, ProductAttributeImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, ProductAttribute.class, ProductAttributeImpl.class);
    }

    @Override
    public void addFetch(Root<ProductAttributeImpl> root) {
        root.fetch("measurements", JoinType.LEFT);
    }
}