package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductDefault;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public ProductDefault findById(long id) {
        return em.find(ProductDefault.class, id);
    }

    @Override
    public List<ProductDefault> findAll() {
        return em.createQuery("SELECT i FROM ProductDefault i",ProductDefault.class).getResultList();
    }

    @Override
    public void save(ProductDefault item) {
         em.persist(item);
    }

    @Override
    public void remove(ProductDefault item) {
        em.remove(item);
    }
}