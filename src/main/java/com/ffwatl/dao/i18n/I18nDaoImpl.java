package com.ffwatl.dao.i18n;

import com.ffwatl.manage.entities.i18n.I18n;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class I18nDaoImpl implements I18nDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<I18n> findByEn(String en) {
        return em.createQuery("SELECT t FROM I18n t WHERE t.en=:en", I18n.class)
                .setParameter("en", en).getResultList();
    }

    @Override
    public void save(I18n t) {
        em.persist(t);
    }

    @Override
    public void remove(I18n t) {
        em.remove(t);
    }
}