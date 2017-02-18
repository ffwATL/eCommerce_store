package com.ffwatl.admin.offer.dao;


import com.ffwatl.admin.offer.domain.OfferAudit;
import com.ffwatl.admin.offer.domain.OfferAuditImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("offer_audit_dao")
public class OfferAuditDaoImpl implements OfferAuditDao{

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;


    @Override
    public OfferAudit findAuditById(long id) {
        return em.find(OfferAuditImpl.class, id);
    }

    @Override
    public OfferAudit save(OfferAudit offerAudit) {
        return em.merge(offerAudit);
    }

    @Override
    public void delete(OfferAudit offerAudit) {
        em.remove(offerAudit);
    }

    @Override
    public OfferAudit create() {
        return (OfferAudit) entityConfiguration.createEntityInstance(OfferAudit.class.getName());
    }

    @Override
    public long countUsesByCustomer(long customerId, long offerId) {
        return (long) em.createQuery("SELECT COUNT(o.customerId) FROM OfferAuditImpl o " +
                "JOIN UserImpl u ON o.customerId=u.id WHERE o.customerId=:customerId AND o.offerId=:offerId")
                .setParameter("customerId", customerId)
                .setParameter("offerId", offerId)
                .getSingleResult();
    }
}