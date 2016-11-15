package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductClothes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class ClothesItemDaoImpl implements ClothesItemDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ProductClothes findById(long id) {
        return em.find(ProductClothes.class, id);
    }

    @Override
    public List<ProductClothes> findAll() {
        return em.createQuery("SELECT i FROM ProductClothes i", ProductClothes.class).getResultList();
    }

    @Override
    public void save(ProductClothes item) {
        em.persist(item);
    }

    @Override
    public void remove(ProductClothes item) {
        em.remove(item);
    }
}