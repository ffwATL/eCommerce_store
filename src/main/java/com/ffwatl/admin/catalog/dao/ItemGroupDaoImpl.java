package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemGroupDaoImpl implements ItemGroupDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public CategoryImpl findById(long id) {
        return em.find(CategoryImpl.class, id);
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
    public void save(CategoryImpl itemGroup) {
        em.persist(itemGroup);
    }

    @Override
    public void remove(CategoryImpl itemGroup) {
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
    public List<CategoryImpl> findByLvl(int lvl) {
        return em.createQuery("SELECT i FROM CategoryImpl i WHERE i.level=:level", CategoryImpl.class)
                .setParameter("level", lvl)
                .getResultList();
    }

    @Override
    public List<CategoryImpl> findAllUsed() {
        return em.createQuery("SELECT DISTINCT i.itemGroup FROM ProductDefault i", CategoryImpl.class).getResultList();
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

}