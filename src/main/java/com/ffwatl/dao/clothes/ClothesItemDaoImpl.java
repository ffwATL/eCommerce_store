package com.ffwatl.dao.clothes;


import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClothesItemDaoImpl implements ClothesItemDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ClothesItem findById(long id) {
        return em.find(ClothesItem.class, id);
    }

    @Override
    public void save(ClothesItem item) {
        em.persist(item);
    }

    @Override
    public void remove(ClothesItem item) {
        em.remove(item);
    }
}