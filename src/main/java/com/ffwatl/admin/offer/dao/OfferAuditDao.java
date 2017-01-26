package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.OfferAudit;

/**
 *  DAO for auditing what went on with offers being added to an order
 */
public interface OfferAuditDao {

    OfferAudit findAuditById(long id);

    /**
     * Persists an audit record to the database
     */
    OfferAudit save(OfferAudit offerAudit);

    /**
     * Removes an audit record from the database
     */
    void delete(OfferAudit offerAudit);

    /**
     * Creates a new offer audit
     */
    OfferAudit create();

    /**
     * Counts how many times the an offer has been used by a customer
     */
    long countUsesByCustomer(long customerId, long offerId);
}