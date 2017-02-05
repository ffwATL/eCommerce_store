package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.FulfillmentGroupItemImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FulfillmentGroupItemDaoImpl implements FulfillmentGroupItemDao {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;


    @Override
    public FulfillmentGroupItem findFulfillmentGroupItemById(long id) {
        return em.find(FulfillmentGroupItemImpl.class, id);
    }

    @Override
    public FulfillmentGroupItem save(FulfillmentGroupItem fulfillmentGroupItem) {
        return em.merge(fulfillmentGroupItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FulfillmentGroupItem> findFulfillmentGroupItemsForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        return em.createNamedQuery("find_fulfillment_group_items_for_fulfillment_group")
                .setParameter("id", fulfillmentGroup.getId())
                .getResultList();
    }

    @Override
    public void delete(FulfillmentGroupItem fulfillmentGroupItem) {
        em.remove(fulfillmentGroupItem);
    }

    @Override
    public FulfillmentGroupItem create() {
        return ((FulfillmentGroupItem) entityConfiguration.createEntityInstance("com.ffwatl.admin.order.domain.FulfillmentGroupItem"));
    }
}