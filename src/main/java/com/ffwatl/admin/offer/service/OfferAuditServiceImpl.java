package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.dao.OfferAuditDao;
import com.ffwatl.admin.offer.domain.OfferAudit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("offer_audit_service")
public class OfferAuditServiceImpl implements OfferAuditService{

    @Resource(name = "offer_audit_dao")
    private OfferAuditDao offerAuditDao;


    @Override
    public OfferAudit findAuditById(long offerAuditId) {
        return offerAuditDao.findAuditById(offerAuditId);
    }

    @Override
    @Transactional
    public OfferAudit save(OfferAudit offerAudit) {
        return offerAuditDao.save(offerAudit);
    }

    @Override
    @Transactional
    public void delete(OfferAudit offerAudit) {
        offerAuditDao.delete(offerAudit);
    }

    @Override
    public OfferAudit create() {
        return offerAuditDao.create();
    }

    @Override
    public long countUsesByCustomer(long customerId, long offerId) {
        return offerAuditDao.countUsesByCustomer(customerId, offerId);
    }

    @Override
    public long countOfferCodeUses(long offerCodeId) {
        return 0;
    }
}