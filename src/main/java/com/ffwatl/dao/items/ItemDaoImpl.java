package com.ffwatl.dao.items;

import com.ffwatl.admin.entities.items.DefaultItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public DefaultItem findById(long id) {
        return em.find(DefaultItem.class, id);
    }

    @Override
    public List<DefaultItem> findAll() {
        return em.createQuery("SELECT i FROM DefaultItem i",DefaultItem.class).getResultList();
    }

    @Override
    public void save(DefaultItem item) {
         em.persist(item);
    }

    @Override
    public void remove(DefaultItem item) {
        em.remove(item);
    }
}