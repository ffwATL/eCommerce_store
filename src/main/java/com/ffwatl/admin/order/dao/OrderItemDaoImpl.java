package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemImpl;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import com.ffwatl.common.FetchMode;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.CriteriaPropertyImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;


@Repository("order_item_dao")
public class OrderItemDaoImpl implements OrderItemDao, FetchModeOption<OrderItem, OrderItemImpl> {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;

    @Override
    public OrderItem findOrderItemById(final FetchMode fetchMode, final long id) {
        final CriteriaProperty<OrderItem, OrderItemImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<OrderItem> criteria = property.getCriteria();
        CriteriaBuilder cb = property.getBuilder();

        // We only want results that match the order IDs
        criteria.where(cb.equal(property.getRoot().get("id"), id));
        List<OrderItem> orderItems = em.createQuery(criteria).getResultList();
        return orderItems != null && orderItems.size() > 0 ? orderItems.get(0) : null;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return em.merge(orderItem);
    }

    @Override
    public void delete(OrderItem orderItem) {
        if (!em.contains(orderItem)) {
            orderItem = findOrderItemById(FetchMode.FETCHED, orderItem.getId());
        }

        em.remove(orderItem);
        em.flush();
    }

    @Override
    public OrderItem create() {
        return (OrderItem) entityConfiguration.createEntityInstance(OrderItem.class.getName());
    }

    @Override
    public OrderItemPriceDetail createOrderItemPriceDetail() {
        return (OrderItemPriceDetail) entityConfiguration.createEntityInstance(OrderItemPriceDetail.class.getName());
    }

    @Override
    public OrderItemQualifier createOrderItemQualifier() {
        return (OrderItemQualifier) entityConfiguration.createEntityInstance(OrderItemQualifier.class.getName());
    }

    @Override
    public OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item) {
        OrderItemPriceDetail detail = createOrderItemPriceDetail()
                .setOrderItem(item)
                .setQuantity(item.getQuantity());

        item.getOrderItemPriceDetails().add(detail);
        return detail;
    }

    @Override
    public CriteriaProperty<OrderItem, OrderItemImpl> createOrderCriteriaQueryByFetchMode(final FetchMode fetchMode){
        final CriteriaBuilder builder = em.getCriteriaBuilder();

        final CriteriaQuery<OrderItem> criteria = builder.createQuery(OrderItem.class);
        final Root<OrderItemImpl> root = criteria.from(OrderItemImpl.class);

        if(fetchMode == FetchMode.FETCHED){
            Fetch<OrderItem, OrderItemPriceDetail> orderItemFetch = root.fetch("orderItemPriceDetails",
                    JoinType.LEFT);

            orderItemFetch.fetch("orderItemPriceDetailAdjustments", JoinType.LEFT);

            root.fetch("candidateItemOffers", JoinType.LEFT);
            root.fetch("orderItemQualifiers", JoinType.LEFT);
        }

        criteria.select(root);
        return new CriteriaPropertyImpl<OrderItem, OrderItemImpl>()
                .setBuilder(builder)
                .setCriteria(criteria)
                .setRoot(root);
    }
}