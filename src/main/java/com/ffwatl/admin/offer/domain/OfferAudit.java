package com.ffwatl.admin.offer.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * Captures when an offer was applied to a customer.
 *
 * Utilized by the offer process to enforce max use by customer rules and as
 * a high-level audit of what orders and customers have used an offer.
 *
 */
public interface OfferAudit extends Serializable{

    long getId();

    /**
     * Returns the associated offer id.
     * @return The associated offer id.
     */
    long getOfferId();
    /**
     * Returns the offer code that was used to retrieve the offer. This will be 0 if the offer was
     * automatically applied and not obtained by an {@link OfferCode}.
     * @return The offer code that was used to retrieve the offer
     */
    long getOfferCodeId();

    /**
     * Returns the associated order id.
     * @return The associated order id.
     */
    long getOrderId();

    /**
     * Returns the id of the associated customer.
     * @return The id of the associated customer.
     */
    long getCustomerId();

    /**
     * Returns the date the offer was applied to the order.
     * @return The date the offer was applied to the order.
     */
    Date getRedeemedDate();


    OfferAudit setId(long id);

    OfferAudit setOfferId(long offerId);

    OfferAudit setOfferCodeId(long offerCodeId);

    OfferAudit setOrderId(long orderId);

    OfferAudit setCustomerId(long customerId);

    OfferAudit setRedeemedDate(Date redeemedDate);
}