package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.BrandImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("brand_dao")
public class BrandDaoImpl implements BrandDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public BrandImpl findById(long id) {
        return em.find(BrandImpl.class, id);
    }

    @Override
    public void save(BrandImpl brand) {
        em.persist(brand);
    }

    @Override
    public List<BrandImpl> findByName(String name) {
        return em.createQuery("SELECT b FROM BrandImpl b WHERE b.name=:name", BrandImpl.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<BrandImpl> findAll() {
        return em.createQuery("SELECT b FROM BrandImpl b", BrandImpl.class).getResultList();
    }

    @Override
    public void remove(BrandImpl brand) {
        em.remove(brand);
    }

    @Override
    public List<BrandImpl> findAllUsed(){
        return em.createQuery("SELECT DISTINCT i.brand FROM ProductClothes i", BrandImpl.class).getResultList();
    }
}