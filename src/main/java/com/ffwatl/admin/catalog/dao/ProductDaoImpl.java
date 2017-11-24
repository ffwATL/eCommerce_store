package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

@Repository("product_dao")
public class ProductDaoImpl implements ProductDao, FetchModeOption<Product, ProductImpl> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product findById(long id, FetchMode fetchMode) {
        CriteriaProperty<Product, ProductImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductImpl> root = property.getRoot();
        CriteriaQuery<Product> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("id"), id));

        List<Product> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Product> findAll(FetchMode fetchMode) {
        CriteriaProperty<Product, ProductImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);

        return em.createQuery(property.getCriteria()).getResultList();
    }

    @Override
    public Product save(Product item) {
        return em.merge(item);
    }

    @Override
    public void remove(Product item) {
        if(!em.contains(item)){
            item = findById(item.getId(), FetchMode.LAZY);
        }
        em.remove(item);
    }

    @Override
    public List<Product> findByStatus(boolean isActive, FetchMode fetchMode) {
        CriteriaProperty<Product, ProductImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductImpl> root = property.getRoot();
        CriteriaQuery<Product> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("isActive"), isActive));

        List<Product> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public CriteriaProperty<Product, ProductImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, Product.class, ProductImpl.class);
    }

    @Override
    public void addFetch(Root<ProductImpl> root) {
        root.fetch("productAttributes", JoinType.LEFT);
    }

}