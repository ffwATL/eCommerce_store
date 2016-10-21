package com.ffwatl.dao.clothes;


import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;
import com.ffwatl.manage.entities.items.clothes.size.Size;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EuroSizeDaoImpl implements EuroSizeDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<EuroSize> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT e FROM EuroSize e WHERE e.cat=:cat", EuroSize.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public EuroSize findById(long id) {
        return em.find(EuroSize.class, id);
    }

    @Override
    public void save(EuroSize eu) {
        em.persist(eu);
    }

    @Override
    public void removeSizeById(long id) {
        em.remove(findById(id));
    }

    @Override
    public List<EuroSize> findAll() {
        return em.createQuery("SELECT e FROM EuroSize e", EuroSize.class).getResultList();
    }

    @Override
    public List<EuroSize> findAllUsed() {
        return em.createQuery("SELECT DISTINCT s.eu_size FROM Size s", EuroSize.class).getResultList();
    }
}
