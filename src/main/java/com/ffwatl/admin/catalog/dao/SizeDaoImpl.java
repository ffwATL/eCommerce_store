package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Size;
import com.ffwatl.admin.catalog.domain.SizeImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SizeDaoImpl implements SizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Size findById(long id) {
        return em.find(SizeImpl.class, id);
    }

    @Override
    public void removeById(long id) {
        em.remove(findById(id));
    }
}