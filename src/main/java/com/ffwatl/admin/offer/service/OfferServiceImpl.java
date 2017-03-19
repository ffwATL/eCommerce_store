package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.dao.CustomerOfferDao;
import com.ffwatl.admin.offer.dao.OfferCodeDao;
import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateItemOffer;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateOrderOffer;
import com.ffwatl.admin.offer.service.discount.PromotableItemFactory;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.offer.service.processor.FulfillmentGroupOfferProcessor;
import com.ffwatl.admin.offer.service.processor.ItemOfferProcessor;
import com.ffwatl.admin.offer.service.processor.OrderOfferProcessor;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.persistence.FetchMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("offer_service")
@Transactional(readOnly = true)
public class OfferServiceImpl implements OfferService {

    private static final Logger LOG = LogManager.getLogger();

    // should be called outside of Offer profile after Offer profile is executed
    @Resource(name = "customer_offer_dao")
    private CustomerOfferDao customerOfferDao;

    @Resource(name = "offer_code_dao")
    private OfferCodeDao offerCodeDao;

    @Resource(name = "offer_audit_service")
    private OfferAuditService offerAuditService;

    @Resource(name = "offer_dao")
    private OfferDao offerDao;

    @Resource(name = "order_offer_processor")
    private OrderOfferProcessor orderOfferProcessor;

    @Resource(name="item_offer_processor")
    private ItemOfferProcessor itemOfferProcessor;

    @Resource(name="fulfillment_group_offer_processor")
    private FulfillmentGroupOfferProcessor fulfillmentGroupOfferProcessor;

    @Resource(name="promotable_item_factory")
    private PromotableItemFactory promotableItemFactory;

    @Resource(name = "offer_service_extension_manager")
    private OfferServiceExtensionManager extensionManager;

    @Resource(name = "order_service")
    private OrderService orderService;



    @Override
    public List<Offer> findAllOffers(FetchMode fetchMode) {
        return fetchMode != null ? offerDao.findAllOffers(fetchMode) : offerDao.findAllOffers(FetchMode.LAZY);
    }

    @Override
    @Transactional
    public Offer save(Offer offer) {
        return offerDao.save(offer);
    }

    @Override
    @Transactional
    public OfferCode saveOfferCode(OfferCode offerCode) {
        /*offerCode.setOffer(offerDao.save(offerCode.getOffer()));*/
        return offerCodeDao.save(offerCode);
    }


    @Override
    public Offer lookupOfferByCode(String code, FetchMode fetchMode) {
        Offer offer = null;
        OfferCode offerCode = offerCodeDao.findOfferCodeByCode(code);
        if (offerCode != null) {
            offer = offerCode.getOffer();
        }
        return offer;
    }

    @Override
    public OfferCode findOfferCodeById(long id) {
        return offerCodeDao.findOfferCodeById(id);
    }

    @Override
    public OfferCode lookupOfferCodeByCode(String code) {
        return offerCodeDao.findOfferCodeByCode(code);
    }

    /*
     *
     * Offers Logic:
     * 1) Remove all existing offers in the Order (order, item, and fulfillment)
     * 2) Check and remove offers
     *    a) Remove out of date offers
     *    b) Remove offers that do not apply to this customer
     * 3) Loop through offers
     *    a) Verifies type of offer (order, order item, fulfillment)
     *    b) Verifies if offer can be applies
     *    c) Assign offer to type (order, order item, or fulfillment)
     * 4) Sorts the order and item offers list by priority and then discount
     * 5) Identify the best offers to apply to order item and create adjustments for each item offer
     * 6) Compare order item adjustment price to sales price, and remove adjustments if sale price is better
     * 7) Identify the best offers to apply to the order and create adjustments for each order offer
     * 8) If item contains non-combinable offers remove either the item or order adjustments based on discount value
     * 9) Set final order item prices and reapply order offers
     *
     * Assumptions:
     * 1) % off all items will be created as an item offer with no expression
     * 2) $ off order will be created as an order offer
     * 3) Order offers applies to the best price for each item (not just retail price)
     * 4) Fulfillment offers apply to best price for each item (not just retail price)
     * 5) Stackable only applies to the same offer type (i.e. a not stackable order offer can be used with item offers)
     * 6) Fulfillment offers cannot be not combinable
     * 7) Order offers cannot be FIXED_PRICE
     * 8) FIXED_PRICE offers cannot be stackable
     * 9) Non-combinable offers only apply to the order and order items, fulfillment group offers will always apply
     *
     */
    @Override
    public Order applyAndSaveOffersToOrder(List<Offer> offers, Order order) throws PricingException {
        /*
        TODO rather than a threadlocal, we should update the "shouldPrice" boolean on the profile API to
        use a richer object to describe the parameters of the pricing call. This object would include
        the pricing boolean, but would also include a list of activities to include or exclude in the
        call
         */

        OfferContext offerContext = OfferContext.getOfferContext();
        if (offerContext == null || offerContext.executePromotionCalculation) {
            PromotableOrder promotableOrder = promotableItemFactory.createPromotableOrder(order, false);
            List<Offer> filteredOffers = orderOfferProcessor.filterOffers(offers, order.getCustomer());
            if ((filteredOffers == null) || (filteredOffers.isEmpty())) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("No offers applicable to this order.");
                }
            } else {
                List<PromotableCandidateOrderOffer> qualifiedOrderOffers = new ArrayList<>();
                List<PromotableCandidateItemOffer> qualifiedItemOffers = new ArrayList<>();

                itemOfferProcessor.filterOffers(promotableOrder, filteredOffers,
                        qualifiedOrderOffers, qualifiedItemOffers);

                if (! (qualifiedItemOffers.isEmpty() && qualifiedOrderOffers.isEmpty())) {
                    // At this point, we should have a PromotableOrder that contains PromotableItems each of which
                    // has a list of candidatePromotions that might be applied.

                    // We also have a list of orderOffers that might apply and a list of itemOffers that might apply.
                    itemOfferProcessor.applyAndCompareOrderAndItemOffers(promotableOrder, qualifiedOrderOffers, qualifiedItemOffers);
                }
            }
            orderOfferProcessor.synchronizeAdjustmentsAndPrices(promotableOrder);

            verifyAdjustments(order, true);

            order.setSubTotal(order.calculateSubTotal());

            /*order = orderService.save(order, false);*/

            boolean madeChange = verifyAdjustments(order, false);
            if (madeChange) {
                order = orderService.save(order, false);
            }
        }

        return order;
    }

    protected boolean verifyAdjustments(Order order, boolean beforeSave) {
        boolean madeChange = false;

        if (order == null || order.getOrderItems() == null) {
            return madeChange;
        }

        for (OrderItem oi : order.getOrderItems()) {
            if (oi.getOrderItemPriceDetails() == null) {
                continue;
            }

            for (OrderItemPriceDetail pd : oi.getOrderItemPriceDetails()) {
                if (pd.getOrderItemPriceDetailAdjustments() == null) {
                    continue;
                }

                Map<Long, OrderItemPriceDetailAdjustment> adjs = new HashMap<>();
                List<OrderItemPriceDetailAdjustment> adjustmentsToRemove = new ArrayList<>();
                for (OrderItemPriceDetailAdjustment adj : pd.getOrderItemPriceDetailAdjustments()) {
                    if (adjs.containsKey(adj.getOffer().getId())) {
                        adjustmentsToRemove.add(adj);
                        if (LOG.isDebugEnabled()) {
                            StringBuilder sb = new StringBuilder("Detected collisions ")
                                    .append(beforeSave ? "before saving" : "after saving")
                                    .append(" with ids ")
                                    .append(adjs.get(adj.getOffer().getId()).getId())
                                    .append(" and ")
                                    .append(adj.getId());
                            LOG.debug(sb.toString());
                        }
                    } else {
                        adjs.put(adj.getOffer().getId(), adj);
                    }
                }

                for (OrderItemPriceDetailAdjustment adj : adjustmentsToRemove) {
                    pd.getOrderItemPriceDetailAdjustments().remove(adj);
                    madeChange = true;
                }
            }
        }

        return madeChange;
    }

    @Override
    public List<Offer> buildOfferListForOrder(Order order) {
        return null;
    }

    @Override
    public List<OfferCode> buildOfferCodeListForCustomer(User customer) {
        return null;
    }

    @Override
    public CustomerOfferDao getCustomerOfferDao() {
        return null;
    }

    @Override
    public OfferCodeDao getOfferCodeDao() {
        return null;
    }

    @Override
    public OfferDao getOfferDao() {
        return null;
    }

    @Override
    public OrderOfferProcessor getOrderOfferProcessor() {
        return null;
    }

    @Override
    public ItemOfferProcessor getItemOfferProcessor() {
        return null;
    }

    @Override
    public FulfillmentGroupOfferProcessor getFulfillmentGroupOfferProcessor() {
        return null;
    }

    @Override
    public Order applyAndSaveFulfillmentGroupOffersToOrder(List<Offer> offers, Order order) {
        return null;
    }

    @Override
    public boolean verifyMaxCustomerUsageThreshold(User customer, Offer offer) {
        return false;
    }

    @Override
    public boolean verifyMaxCustomerUsageThreshold(User customer, OfferCode code) {
        return false;
    }

    @Override
    public Set<Offer> getUniqueOffersFromOrder(Order order) {
        return null;
    }

    @Override
    public Map<Offer, OfferCode> getOfferRetrievedFromCode(OfferCode code, Set<Offer> appliedOffers) {
        return null;
    }

    @Override
    public Map<Offer, OfferCode> getOfferRetrievedFromCode(Order order) {
        return null;
    }

    @Override
    public OrderService getOrderService() {
        return null;
    }
}
