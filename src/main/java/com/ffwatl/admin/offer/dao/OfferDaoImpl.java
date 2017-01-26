package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class OfferDaoImpl implements OfferDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Offer> findAllOffers() {
        return em.createNamedQuery("findAllOffers").getResultList();
    }

    @Override
    public Offer findOfferById(long id) {
        return em.find(OfferImpl.class, id);
    }

    @Override
    public List<Offer> findOffersByAutomaticDeliveryType() {
        return em.createNamedQuery("findOffersByAutomaticDeliveryType").getResultList();
    }

    @Override
    public Offer save(Offer offer) {
        return em.merge(offer);
    }

    @Override
    public void delete(Offer offer) {
        em.remove(offer);
    }

    @Override
    public Offer create() {
        return null;
    }

    @Override
    public CandidateOrderOffer createCandidateOrderOffer() {
        return null;
    }

    @Override
    public CandidateItemOffer createCandidateItemOffer() {
        return null;
    }

    @Override
    public CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer() {
        return null;
    }

    @Override
    public OrderItemAdjustment createOrderItemAdjustment() {
        return null;
    }

    @Override
    public OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment() {
        return null;
    }

    @Override
    public OrderAdjustment createOrderAdjustment() {
        return null;
    }

    @Override
    public FulfillmentGroupAdjustment createFulfillmentGroupAdjustment() {
        return null;
    }
}