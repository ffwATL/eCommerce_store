package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeTypeImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EuroSizeDaoImpl implements EuroSizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductAttributeTypeImpl> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT e FROM EuroSizeImpl e WHERE e.cat=:cat", ProductAttributeTypeImpl.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public ProductAttributeTypeImpl findById(long id) {
        return em.find(ProductAttributeTypeImpl.class, id);
    }

    @Override
    public void save(ProductAttributeTypeImpl eu) {
        em.persist(eu);
    }

    @Override
    public void removeSizeById(long id) {
        em.remove(findById(id));
    }

    @Override
    public List<ProductAttributeTypeImpl> findAll() {
        return em.createQuery("SELECT e FROM EuroSizeImpl e", ProductAttributeTypeImpl.class).getResultList();
    }

    @Override
    public List<ProductAttributeTypeImpl> findAllUsed() {
        return em.createQuery("SELECT DISTINCT s.eu_size FROM SizeImpl s", ProductAttributeTypeImpl.class).getResultList();
    }
}
