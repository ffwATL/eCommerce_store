package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupImpl;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.FulfillmentGroupStatusType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FulfillmentGroupDaoImpl implements FulfillmentGroupDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public FulfillmentGroup findFulfillmentGroupById(long id) {
        return em.createQuery("SELECT f FROM FulfillmentGroupImpl f " +
                "LEFT JOIN FETCH f.candidateFulfillmentGroupOffers " +
                "LEFT JOIN FETCH f.fulfillmentGroupAdjustments " +
                "WHERE f.id=:id", FulfillmentGroupImpl.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public FulfillmentGroup save(FulfillmentGroup fulfillmentGroup) {
        return em.merge(fulfillmentGroup);
    }

    @Override
    public FulfillmentGroup findDefaultFulfillmentGroupForOrder(Order order) {
        if(order == null) throw new IllegalArgumentException("Order can't be null!");

        return em.createQuery("SELECT f FROM FulfillmentGroupImpl f " +
                "LEFT JOIN FETCH f.candidateFulfillmentGroupOffers " +
                "LEFT JOIN FETCH f.fulfillmentGroupAdjustments " +
                "WHERE f.order.orderNumber=:orderNumber", FulfillmentGroupImpl.class)
                .setParameter("orderNumber", order.getOrderNumber())
                .getSingleResult();
    }

    @Override
    public void delete(FulfillmentGroup fulfillmentGroup) {

    }

    @Override
    public FulfillmentGroup createDefault() {
        return null;
    }

    @Override
    public FulfillmentGroup create() {
        return null;
    }

    @Override
    public List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults) {
        return null;
    }

    @Override
    public List<FulfillmentGroup> findPartiallyFulfilledFulfillmentGroups(int start, int maxResults) {
        return null;
    }

    @Override
    public List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults) {
        return null;
    }

    @Override
    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults, boolean ascending) {
        return null;
    }

    @Override
    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults) {
        return null;
    }

    @Override
    public int findNextFulfillmentGroupSequnceForOrder(Order order) {
        return 0;
    }

}