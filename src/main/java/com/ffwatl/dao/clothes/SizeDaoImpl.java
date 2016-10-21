package com.ffwatl.dao.clothes;


import com.ffwatl.manage.entities.items.clothes.size.Size;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SizeDaoImpl implements SizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Size findById(long id) {
        return em.find(Size.class, id);
    }

    @Override
    public void removeById(long id) {
        em.remove(findById(id));
    }
}