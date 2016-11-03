package com.ffwatl.dao.clothes;

import com.ffwatl.admin.entities.items.brand.Brand;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BrandDaoImpl implements BrandDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Brand findById(long id) {
        return em.find(Brand.class, id);
    }

    @Override
    public void save(Brand brand) {
        em.persist(brand);
    }

    @Override
    public List<Brand> findByName(String name) {
        return em.createQuery("SELECT b FROM Brand b WHERE b.name=:name", Brand.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Brand> findAll() {
        return em.createQuery("SELECT b FROM Brand b", Brand.class).getResultList();
    }

    @Override
    public void remove(Brand brand) {
        em.remove(brand);
    }

    @Override
    public List<Brand> findAllUsed(){
        return em.createQuery("SELECT DISTINCT i.brand FROM ClothesItem i", Brand.class).getResultList();
    }
}