package com.ffwatl.dao.items;

import com.ffwatl.admin.entities.items.color.Color;
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
        return em.find(Color.class, id);
    }

    @Override
    public void save(Color c) {
        em.persist(c);
    }

    @Override
    public List<Color> findAll() {
        return em.createQuery("SELECT c FROM Color c", Color.class).getResultList();
    }

    @Override
    public List<Color> findAllUsed() {
        return em.createQuery("SELECT DISTINCT i.color FROM DefaultItem i",Color.class).getResultList();
    }
}
