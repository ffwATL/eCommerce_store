package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.EuroSize;
import com.ffwatl.admin.catalog.domain.EuroSizeImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EuroSizeDaoImpl implements EuroSizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EuroSizeImpl> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT e FROM EuroSizeImpl e WHERE e.cat=:cat", EuroSizeImpl.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public EuroSizeImpl findById(long id) {
        return em.find(EuroSizeImpl.class, id);
    }

    @Override
    public void save(EuroSizeImpl eu) {
        em.persist(eu);
    }

    @Override
    public void removeSizeById(long id) {
        em.remove(findById(id));
    }

    @Override
    public List<EuroSizeImpl> findAll() {
        return em.createQuery("SELECT e FROM EuroSizeImpl e", EuroSizeImpl.class).getResultList();
    }

    @Override
    public List<EuroSizeImpl> findAllUsed() {
        return em.createQuery("SELECT DISTINCT s.eu_size FROM SizeImpl s", EuroSizeImpl.class).getResultList();
    }
}
