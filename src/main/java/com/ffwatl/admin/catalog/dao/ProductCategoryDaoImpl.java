package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.ProductCategoryImpl;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

@Repository("product_category_dao")
public class ProductCategoryDaoImpl implements ProductCategoryDao, FetchModeOption<ProductCategory, ProductCategoryImpl> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public ProductCategory findById(long id, FetchMode fetchMode) {
        CriteriaProperty<ProductCategory, ProductCategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductCategoryImpl> root = property.getRoot();
        CriteriaQuery<ProductCategory> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("id"), id));

        List<ProductCategory> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<ProductCategory> findByName(String name, FetchMode fetchMode) {
        CriteriaProperty<ProductCategory, ProductCategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductCategoryImpl> root = property.getRoot();
        CriteriaQuery<ProductCategory> criteria = property.getCriteria();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.or(
                cb.equal(root.get("groupName").get("locale_en"), name),
                cb.equal(root.get("groupName").get("locale_ru"), name),
                cb.equal(root.get("groupName").get("locale_ua"), name)));

        List<ProductCategory> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public ProductCategory save(ProductCategory itemGroup) {
        return em.merge(itemGroup);
    }

    @Override
    public void remove(ProductCategory itemGroup) {
        if(!em.contains(itemGroup)){
            itemGroup = findById(itemGroup.getId(), FetchMode.LAZY);
        }
        em.remove(itemGroup);
    }

    @Override
    public List<ProductCategoryImpl> findByLevelAndByName(int level, String name) {
        return em.createQuery("SELECT i FROM ProductCategoryImpl i WHERE i.level=:level AND (i.groupName.locale_en=:name"+
                " OR i.groupName.locale_ru=:name" +
                " OR i.groupName.locale_ua=:name)", ProductCategoryImpl.class)
                .setParameter("level", level)
                .setParameter("name", name)
                .getResultList();
    }


    @Override
    public List<ProductCategoryImpl> findByLevel(int level) {
        return em.createQuery("SELECT i FROM ProductCategoryImpl i WHERE i.level=:level", ProductCategoryImpl.class)
                .setParameter("level", level)
                .getResultList();
    }

    @Override
    public List<ProductCategory> findByLevel(int level, FetchMode fetchMode) {
        CriteriaProperty<ProductCategory, ProductCategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductCategoryImpl> root = property.getRoot();
        CriteriaQuery<ProductCategory> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("level"), level));

        List<ProductCategory> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public List<ProductCategory> findAllInUse(FetchMode fetchMode) {
        if(fetchMode == FetchMode.LAZY){
            return em.createQuery("SELECT DISTINCT i.productCategory FROM ProductImpl i", ProductCategory.class).getResultList();
        }else {
            return em.createQuery("SELECT DISTINCT i.productCategory FROM ProductImpl i " +
                    "JOIN FETCH i.productCategory.child", ProductCategory.class).getResultList();
        }
    }

    @Override
    public List<ProductCategory> findByLevelAndName(int level, String name, FetchMode fetchMode) {
        CriteriaProperty<ProductCategory, ProductCategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<ProductCategoryImpl> root = property.getRoot();
        CriteriaQuery<ProductCategory> criteria = property.getCriteria();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.and(
                cb.equal(root.get("level"), level),
                cb.or(
                        cb.equal(root.get("groupName").get("locale_en"), name),
                        cb.equal(root.get("groupName").get("locale_ru"), name),
                        cb.equal(root.get("groupName").get("locale_ua"), name))));

        List<ProductCategory> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public List<ProductCategoryImpl> findByLvlFetchedChild(int level){
        return em.createQuery("SELECT i FROM ProductCategoryImpl i LEFT JOIN FETCH i.child " +
                "WHERE i.level=:level", ProductCategoryImpl.class)
                .setParameter("level", level)
                .getResultList();
    }

    @Override
    public CriteriaProperty<ProductCategory, ProductCategoryImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, ProductCategory.class, ProductCategoryImpl.class);
    }

    @Override
    public void addFetch(Root<ProductCategoryImpl> root) {
        root.fetch("child", JoinType.LEFT);
    }
}