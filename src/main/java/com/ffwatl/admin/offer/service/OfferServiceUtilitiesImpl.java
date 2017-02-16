package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.offer.service.discount.*;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import com.ffwatl.common.rule.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("offer_service_utilities")
public class OfferServiceUtilitiesImpl implements OfferServiceUtilities{

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "promotable_item_factory")
    private PromotableItemFactory promotableItemFactory;

    @Resource(name = "offer_dao")
    private OfferDao offerDao;

    @Resource(name = "offer_service_extension_manager")
    private OfferServiceExtensionManager offerServiceExtensionManager;


    public PromotableItemFactory getPromotableItemFactory() {
        return promotableItemFactory;
    }

    public void setPromotableItemFactory(PromotableItemFactory promotableItemFactory) {
        this.promotableItemFactory = promotableItemFactory;
    }

    public OfferDao getOfferDao() {
        return offerDao;
    }

    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    public OfferServiceExtensionManager getOfferServiceExtensionManager() {
        return offerServiceExtensionManager;
    }

    public void setOfferServiceExtensionManager(OfferServiceExtensionManager offerServiceExtensionManager) {
        this.offerServiceExtensionManager = offerServiceExtensionManager;
    }

    @Override
    public void sortTargetItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice) {

    }

    @Override
    public void sortQualifierItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice) {

    }

    @Override
    public OrderItem findRelatedQualifierRoot(OrderItem relatedQualifier) {
        return null;
    }

    @Override
    public boolean itemOfferCanBeApplied(PromotableCandidateItemOffer itemOffer, List<PromotableOrderItemPriceDetail> details) {
        return false;
    }

    @Override
    public int markQualifiersForCriteria(PromotableCandidateItemOffer itemOffer, Rule rule, List<PromotableOrderItemPriceDetail> priceDetails) {
        return 0;
    }

    @Override
    public int markTargetsForCriteria(PromotableCandidateItemOffer itemOffer, OrderItem relatedQualifier, boolean checkOnly, Offer promotion, OrderItem relatedQualifierRoot, Rule rule, List<PromotableOrderItemPriceDetail> priceDetails, int targetQtyNeeded) {
        return 0;
    }

    @Override
    public int markRelatedQualifiersAndTargetsForItemCriteria(PromotableCandidateItemOffer itemOffer, PromotableOrder order, OrderItem orderItem, Rule rule, List<PromotableOrderItemPriceDetail> priceDetails) {
        return 0;
    }

    @Override
    public void applyAdjustmentsForItemPriceDetails(PromotableCandidateItemOffer itemOffer, List<PromotableOrderItemPriceDetail> itemPriceDetails) {

    }

    @Override
    public void applyOrderItemAdjustment(PromotableCandidateItemOffer itemOffer, PromotableOrderItemPriceDetail itemPriceDetail) {

    }

    @Override
    public List<OrderItem> buildOrderItemList(Order order) {
        return null;
    }

    @Override
    public Map<OrderItem, PromotableOrderItem> buildPromotableItemMap(PromotableOrder promotableOrder) {
        return null;
    }

    @Override
    public Map<Long, OrderItemPriceDetailAdjustment> buildItemDetailAdjustmentMap(OrderItemPriceDetail itemDetail) {
        return null;
    }

    @Override
    public void updatePriceDetail(OrderItemPriceDetail itemDetail, PromotableOrderItemPriceDetail promotableDetail) {

    }

    @Override
    public void removeUnmatchedPriceDetails(Map<Long, ? extends OrderItemPriceDetail> unmatchedDetailsMap, Iterator<? extends OrderItemPriceDetail> pdIterator) {

    }

    @Override
    public void removeUnmatchedQualifiers(Map<Long, ? extends OrderItemQualifier> unmatchedQualifiersMap, Iterator<? extends OrderItemQualifier> qIterator) {

    }
}
