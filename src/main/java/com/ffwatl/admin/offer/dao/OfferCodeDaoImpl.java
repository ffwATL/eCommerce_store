package com.ffwatl.admin.offer.dao;


import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.domain.OfferCodeImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("offer_code_dao")
public class OfferCodeDaoImpl implements OfferCodeDao{

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;

    @Override
    public OfferCode findOfferCodeById(long id) {
        return em.find(OfferCodeImpl.class, id);
    }

    @Override
    public OfferCode findOfferCodeByCode(String code) {
        return em.createQuery("SELECT o FROM OfferCodeImpl o WHERE o.offerCode=:code", OfferCodeImpl.class)
                .setParameter("code", code)
                .getSingleResult();
    }

    @Override
    public OfferCode save(OfferCode offerCode) {
        return em.merge(offerCode);
    }

    @Override
    public void delete(OfferCode offerCode) {
        em.remove(offerCode);
    }

    @Override
    public OfferCode create() {
        return (OfferCode) entityConfiguration.createEntityInstance(OfferCode.class.getName());
    }
}