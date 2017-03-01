package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.dao.CustomerOfferDao;
import com.ffwatl.admin.offer.dao.OfferCodeDao;
import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.processor.FulfillmentGroupOfferProcessor;
import com.ffwatl.admin.offer.service.processor.ItemOfferProcessor;
import com.ffwatl.admin.offer.service.processor.OrderOfferProcessor;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OfferService {

    /**
     * Returns all offers;
     * @return all offers.
     */
    List<Offer> findAllOffers(FetchMode fetchMode);

    /**
     * Save a new offer or updates an existing offer;
     * @param offer a new offer to save;
     * @return the offer.
     */
    Offer save(Offer offer);

    /**
     * Saves a new Offer or updates an existing Offer that belongs to an OfferCode, then saves or updates the OfferCode
     * @param offerCode
     * @return the offerCode
     */
    OfferCode saveOfferCode(OfferCode offerCode);

    /**
     * Lookup offer by code.
     * @param code the code
     * @return the offer
     */
    Offer lookupOfferByCode(String code, FetchMode fetchMode);

    /**
     * Lookup an OfferCode by its id
     * @param id the offer code id
     * @return the offer code
     */
    OfferCode findOfferCodeById(long id);

    /**
     * Lookup OfferCode by code.
     * @param code the code
     * @return the offer
     */
    OfferCode lookupOfferCodeByCode(String code);

    /**
     * Apply offers to order. By default this does not re-price the order.
     * @param offers the offers
     * @param order the order
     * @return the updated order
     */
    Order applyAndSaveOffersToOrder(List<Offer> offers, Order order);


    /**
     * Create a list of offers that applies to this order
     * @param order
     * @return
     */
    List<Offer> buildOfferListForOrder(Order order);

    /**
     * Attempts to resolve a list of offer codes associated explicitly with the customer.
     * For example, an implementation may choose to associate a specific offer code with a customer
     * in a custom table or in customer attributes.  This allows you to associate one or more offer codes
     * with a customer without necessarily having them type it in (e.g. on a URL), or by allowing them to
     * type it in, but before it has been actually applied to an order.
     */
    List<OfferCode> buildOfferCodeListForCustomer(User customer);

    CustomerOfferDao getCustomerOfferDao();

    OfferCodeDao getOfferCodeDao();

    OfferDao getOfferDao();

    OrderOfferProcessor getOrderOfferProcessor();

    ItemOfferProcessor getItemOfferProcessor();

    FulfillmentGroupOfferProcessor getFulfillmentGroupOfferProcessor();

    Order applyAndSaveFulfillmentGroupOffersToOrder(List<Offer> offers, Order order);

    /**
     * <p>Validates that the passed in customer has not exceeded the max uses for the
     * passed in offer.</p>
     *
     * <p>This condition could pass if the system allows two concurrent carts for the same customer.
     * The condition will fail at order submission time when the {@link VerifyCustomerMaxOfferUsesActivity}
     * runs (if that activity is configured as part of the checkout workflow.)</p>
     *
     * <p>This method only checks offers who have a max_customer_uses value that is greater than zero.
     * By default offers can be used as many times as the customer's order qualifies.</p>
     *
     * <p>This method offers no protection against systems that allow customers to create
     * multiple ids in the system.</p>
     *
     * @param customer the customer attempting to use the offer
     * @param offer the offer to check
     * @return <b>true</b> if it is ok for the customer to use this offer with their current order, <b>false</b> if not.
     */
    boolean verifyMaxCustomerUsageThreshold(User customer, Offer offer);

    /**
     * <p>Validates that the given code is underneath the max uses for that code. This method will also delegate to
     * {@link #verifyMaxCustomerUsageThreshold(User, Offer)} for the code's offer and the passed in customer</p>
     *
     * @param customer the customer attempting to use the code
     * @param code the code to check
     * @return <b>true</b> if it is ok for the customer to use this offer with their current order, <b>false</b> if not.
     */
    boolean verifyMaxCustomerUsageThreshold(User customer, OfferCode code);

    /**
     * Returns a set of offers that have been used for this order by checking adjustments on the different levels like
     * FulfillmentGroups and OrderItems. This will return all of the unique offers used for instances where an offer can
     * apply to multiple OrderItems or multiple FulfillmentGroups (and show up as different adjustments on each)
     *
     * @param order
     * @return
     */
    Set<Offer> getUniqueOffersFromOrder(Order order);

    /**
     * Given a list of offer codes and a set of offers, return a map of of offer codes that are keyed by the offer that was
     * applied to the order
     *
     * @param codes
     * @param appliedOffers
     * @return
     */
    Map<Offer, OfferCode> getOffersRetrievedFromCodes(List<OfferCode> codes, Set<Offer> appliedOffers);

    /**
     * For a given order, give back a map of all {@link Offer}s that were retrieved from {@link OfferCode}s. More explicitly,
     * this will look at all of the offers that have been used by looking at a given {@link Order}'s adjustments and then
     * match those up with the codes from {@link Order#getOfferCode()}.
     *
     * @param order
     * @return a map from {@link Offer} to the {@link OfferCode} that was used to obtain it
     */
    Map<Offer, OfferCode> getOffersRetrievedFromCodes(Order order);

    OrderService getOrderService();
}