package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EuroSizeDaoImpl implements EuroSizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductAttributeType> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT e FROM EuroSizeImpl e WHERE e.cat=:cat", ProductAttributeType.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public ProductAttributeType findById(long id) {
        return em.find(ProductAttributeType.class, id);
    }

    @Override
    public void save(ProductAttributeType eu) {
        em.persist(eu);
    }

    @Override
    public void removeSizeById(long id) {
        em.remove(findById(id));
    }

    @Override
    public List<ProductAttributeType> findAll() {
        return em.createQuery("SELECT e FROM EuroSizeImpl e", ProductAttributeType.class).getResultList();
    }

    @Override
    public List<ProductAttributeType> findAllUsed() {
        return em.createQuery("SELECT DISTINCT s.eu_size FROM SizeImpl s", ProductAttributeType.class).getResultList();
    }
}
