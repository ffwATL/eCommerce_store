package com.ffwatl.dao.items;

import com.ffwatl.manage.entities.items.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Item findById(long id) {
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i",Item.class).getResultList();
    }

    @Override
    public void save(Item item) {
         em.persist(item);
    }

    @Override
    public void remove(Item item) {
        em.remove(item);
    }
}