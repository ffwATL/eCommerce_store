package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ColorImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ColorDaoImpl implements ColorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Color findById(long id) {
        return em.find(ColorImpl.class, id);
    }

    @Override
    public void save(Color c) {
        em.persist(c);
    }

    @Override
    public List<ColorImpl> findAll() {
        return em.createQuery("SELECT c FROM ColorImpl c", ColorImpl.class).getResultList();
    }

    @Override
    public List<ColorImpl> findAllUsed() {
        return em.createQuery("SELECT DISTINCT i.color FROM ProductDefault i",ColorImpl.class).getResultList();
    }
}
