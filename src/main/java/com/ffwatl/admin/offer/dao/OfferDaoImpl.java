package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.*;
import com.ffwatl.common.FetchMode;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.CriteriaPropertyImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository("offer_dao")
public class OfferDaoImpl implements OfferDao, FetchModeOption<Offer, OfferImpl> {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;

    @Override
    public List<Offer> findAllOffers(FetchMode fetchMode) {
        CriteriaProperty<Offer, OfferImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);

        return em.createQuery(property.getCriteria()).getResultList();
    }

    @Override
    public Offer findOfferById(long id, FetchMode fetchMode) {
        CriteriaProperty<Offer, OfferImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<Offer> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(property.getRoot().get("id"), id));
        List<Offer> list = em.createQuery(criteria).getResultList();

        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Offer> findOffersByAutomaticDeliveryType(FetchMode fetchMode) {
        CriteriaProperty<Offer, OfferImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<Offer> criteria = property.getCriteria();

        criteria.where(property.getBuilder().equal(property.getRoot().get("automaticallyAdded"), true));
        return em.createQuery(criteria).getResultList();
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
        return (Offer) entityConfiguration.createEntityInstance(Offer.class.getName());
    }

    @Override
    public CandidateOrderOffer createCandidateOrderOffer() {
        return (CandidateOrderOffer) entityConfiguration.createEntityInstance(CandidateOrderOffer.class.getName());
    }

    @Override
    public CandidateItemOffer createCandidateItemOffer() {
        return (CandidateItemOffer) entityConfiguration.createEntityInstance(CandidateItemOffer.class.getName());
    }

    @Override
    public CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer() {
        return (CandidateFulfillmentGroupOffer) entityConfiguration.createEntityInstance(CandidateFulfillmentGroupOffer.class.getName());
    }

    @Override
    public OrderItemAdjustment createOrderItemAdjustment() {
        return (OrderItemAdjustment) entityConfiguration.createEntityInstance(OrderItemAdjustment.class.getName());
    }

    @Override
    public OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment() {
        return (OrderItemPriceDetailAdjustment) entityConfiguration.createEntityInstance(OrderItemPriceDetailAdjustment.class.getName());
    }

    @Override
    public OrderAdjustment createOrderAdjustment() {
        return (OrderAdjustment) entityConfiguration.createEntityInstance(OrderAdjustment.class.getName());
    }

    @Override
    public FulfillmentGroupAdjustment createFulfillmentGroupAdjustment() {
        return (FulfillmentGroupAdjustment) entityConfiguration.createEntityInstance(FulfillmentGroupAdjustment.class.getName());
    }

    @Override
    public CriteriaProperty<Offer, OfferImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Offer> criteria = cb.createQuery(Offer.class);

        Root<OfferImpl> root = criteria.from(OfferImpl.class);

        if(fetchMode == FetchMode.FETCHED){
            root.fetch("matchRules", JoinType.LEFT); //TODO: It may not working..
            root.fetch("offerCodes", JoinType.LEFT);
        }

        criteria.distinct(true);
        criteria.select(root);
        return new CriteriaPropertyImpl<Offer, OfferImpl>()
                .setCriteria(criteria)
                .setBuilder(cb)
                .setRoot(root);
    }
}