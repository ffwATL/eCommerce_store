package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;
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

@Repository
public class CategoryDaoImpl implements CategoryDao, FetchModeOption<Category, CategoryImpl> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public CategoryImpl findById(long id) {
        return em.find(CategoryImpl.class, id);
    }

    @Override
    public Category findById(long id, FetchMode fetchMode) {
        CriteriaProperty<Category, CategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<CategoryImpl> root = property.getRoot();
        CriteriaQuery<Category> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("id"), id));

        List<Category> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<CategoryImpl> findByName(String name) {
        return em.createQuery("SELECT i FROM CategoryImpl i WHERE " +
                "i.groupName.locale_en=:name " +
                "OR i.groupName.locale_ru=:name " +
                "OR i.groupName.locale_ua=:name", CategoryImpl.class)
                .setParameter("name",name)
                .getResultList();
    }

    @Override
    public List<Category> findByName(String name, FetchMode fetchMode) {
        CriteriaProperty<Category, CategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<CategoryImpl> root = property.getRoot();
        CriteriaQuery<Category> criteria = property.getCriteria();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.or(
                cb.equal(root.get("groupName").get("locale_en"), name),
                cb.equal(root.get("groupName").get("locale_ru"), name),
                cb.equal(root.get("groupName").get("locale_ua"), name)));

        List<Category> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public Category save(Category itemGroup) {
        return em.merge(itemGroup);
    }

    @Override
    public void remove(Category itemGroup) {
        if(!em.contains(itemGroup)){
            itemGroup = findById(itemGroup.getId(), FetchMode.LAZY);
        }
        em.remove(itemGroup);
    }

    @Override
    public List<CategoryImpl> findByLvlAndByName(int lvl, String name) {
        return em.createQuery("SELECT i FROM CategoryImpl i WHERE i.level=:level AND (i.groupName.locale_en=:name"+
                " OR i.groupName.locale_ru=:name" +
                " OR i.groupName.locale_ua=:name)", CategoryImpl.class)
                .setParameter("level", lvl)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<CategoryImpl> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT i FROM CategoryImpl i WHERE i.cat=:cat", CategoryImpl.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public List<Category> findByCommonCategory(CommonCategory cat, FetchMode fetchMode) {
        CriteriaProperty<Category, CategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<CategoryImpl> root = property.getRoot();
        CriteriaQuery<Category> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("cat"), cat));

        List<Category> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public List<CategoryImpl> findByLvl(int lvl) {
        return em.createQuery("SELECT i FROM CategoryImpl i WHERE i.level=:level", CategoryImpl.class)
                .setParameter("level", lvl)
                .getResultList();
    }

    @Override
    public List<Category> findByLevel(int level, FetchMode fetchMode) {
        CriteriaProperty<Category, CategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<CategoryImpl> root = property.getRoot();
        CriteriaQuery<Category> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(root.get("level"), level));

        List<Category> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();
    }

    @Override
    public List<Category> findAllUsed() {
        return em.createQuery("SELECT DISTINCT i.itemGroup FROM ProductImpl i", Category.class).getResultList();
    }

    @Override
    public List<Category> findAllInUse(FetchMode fetchMode) {
        if(fetchMode == FetchMode.LAZY){
            return em.createQuery("SELECT DISTINCT i.itemGroup FROM ProductImpl i", Category.class).getResultList();
        }else {
            return em.createQuery("SELECT DISTINCT i.itemGroup FROM ProductImpl i " +
                    "JOIN FETCH i.itemGroup.child", Category.class).getResultList();
        }
    }

    @Override
    public List<Category> findByLevelAndName(int level, String name, FetchMode fetchMode) {
        CriteriaProperty<Category, CategoryImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        Root<CategoryImpl> root = property.getRoot();
        CriteriaQuery<Category> criteria = property.getCriteria();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.and(
                cb.equal(root.get("level"), level),
                cb.or(
                        cb.equal(root.get("groupName").get("locale_en"), name),
                        cb.equal(root.get("groupName").get("locale_ru"), name),
                        cb.equal(root.get("groupName").get("locale_ua"), name))));

        List<Category> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result : Collections.emptyList();

    }

    @Override
    public CategoryImpl findByLvlAndNameFetched(int lvl, String name){
        return em.createQuery("SELECT i FROM CategoryImpl i LEFT JOIN FETCH i.child WHERE i.level=:lvl " +
                "AND i.groupName.locale_en=:name", CategoryImpl.class)
                .setParameter("lvl",lvl)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<CategoryImpl> findByLvlFetchedChild(int lvl){
        return em.createQuery("SELECT i FROM CategoryImpl i LEFT JOIN FETCH i.child " +
                "WHERE i.level=:lvl", CategoryImpl.class)
                .setParameter("lvl",lvl)
                .getResultList();
    }

    @Override
    public CriteriaProperty<Category, CategoryImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, Category.class, CategoryImpl.class);
    }

    @Override
    public void addFetch(Root<CategoryImpl> root) {
        root.fetch("child", JoinType.LEFT);
    }
}