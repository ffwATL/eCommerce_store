package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.OfferAudit;

/**
 * Service for managing {@link OfferAudit}s. An {@link OfferAudit} is used to track usage of an offer and offer code
 * for a particular {@link com.ffwatl.admin.order.domain.Order} and {@link com.ffwatl.admin.user.domain.User}. This
 * provides easy and fast tracking of verifying max uses on particular {@link com.ffwatl.admin.offer.domain.Offer}s.
 *
 */
public interface OfferAuditService {

    OfferAudit findAuditById(long offerAuditId);

    /**
     * Persists an audit record to the database
     */
    OfferAudit save(OfferAudit offerAudit);

    void delete(OfferAudit offerAudit);

    /**
     * Creates a new offer audit
     */
    OfferAudit create();

    /**
     * Counts how many times the an offer has been used by a customer
     */
    long countUsesByCustomer(long customerId, long offerId, int version);

    /**
     * Counts how many times the given offer code has been used in the system
     */
    long countOfferCodeUses(long offerCodeId, int version);
}