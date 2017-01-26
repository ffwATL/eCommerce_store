package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductAttributeImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SizeDaoImpl implements SizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProductAttribute findById(long id) {
        return em.find(ProductAttributeImpl.class, id);
    }

    @Override
    public void removeById(long id) {
        em.remove(findById(id));
    }
}