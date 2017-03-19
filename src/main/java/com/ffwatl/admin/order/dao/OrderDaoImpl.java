package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderImpl;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.persistence.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository("order_dao")
public class OrderDaoImpl implements OrderDao, FetchModeOption<Order, OrderImpl>{

    private static final String ORDER_LOCK_KEY = UUID.randomUUID().toString();

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;


    @Override
    public Order findOrderById(final long id, final FetchMode fetchMode) {
        List<Order> orders = findOrdersByIds(fetchMode, id);
        return orders.size() > 0 ? orders.get(0) : null;
    }

    @Override
    public Order findOrderById(final long id, final boolean refresh) {
        Order order = findOrderById(id, FetchMode.FETCHED);

        if(refresh){
            em.refresh(order);
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByIds(final FetchMode fetchMode, final long... ids) {
        final CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<Order> criteria = property.getCriteria();

        // We only want results that match the order IDs
        criteria.where(property.getRoot().get("id")
                .as(Long.class)
                .in(ArrayUtils.toObject(ids)));

        return em.createQuery(criteria)
                .getResultList();
    }

    @Override
    public List<Order> findBatchOrders(final int start, final int maxResults, final List<OrderStatus> statuses,
                                       final FetchMode fetchMode) {
        final CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<Order> criteria = property.getCriteria();

        // We only want results that match the order statuses
        criteria.where(property.getRoot().get("orderStatus")
                .as(OrderStatus.class)
                .in(statuses));

        return em.createQuery(criteria)
                .setFirstResult(start)
                .setMaxResults(maxResults)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersForCustomer(final long customerId, final OrderStatus orderStatus,
                                             final FetchMode fetchMode) {
        final CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        final CriteriaQuery<Order> criteria = property.getCriteria();
        final Root<OrderImpl> root = property.getRoot();
        final CriteriaBuilder cb = property.getBuilder();

        final List<Predicate> predicates = new ArrayList<>();
        final Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

        predicates.add(cb.equal(userJoin.get("id"), customerId));

        if(orderStatus != null){
            predicates.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), orderStatus));
        }

        // We only want results that match the order customer and status
        criteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return em.createQuery(criteria)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersForCustomer(long customerId, final FetchMode fetchMode) {
        return findOrdersForCustomer(customerId, null, fetchMode);
    }

    @Override
    public Order findNamedOrderForCustomer(final long customerId, final String name, final FetchMode fetchMode) {
        final CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        final CriteriaQuery<Order> criteria = property.getCriteria();
        final CriteriaBuilder cb = property.getBuilder();
        final Root<OrderImpl> root = property.getRoot();

        final List<Predicate> predicates = new ArrayList<>();
        final Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

        predicates.add(cb.equal(userJoin.get("id"), customerId));
        predicates.add(cb.equal(root.get("name"), name));

        // We only want results that match the order name for given customer
        criteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return em.createQuery(criteria)
                .getSingleResult();
    }

    @Override
    public Order findCartForCustomer(final long customerId, final FetchMode fetchMode) {
        final CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        final CriteriaQuery<Order> criteria = property.getCriteria();
        final CriteriaBuilder cb = property.getBuilder();
        final Root<OrderImpl> root = property.getRoot();

        final List<Predicate> predicates = new ArrayList<>();
        final Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

        predicates.add(cb.equal(userJoin.get("id"), customerId));
        predicates.add(cb.isNull(root.get("name")));
        predicates.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), OrderStatus.IN_PROCESS));

        // We only want results that match the order name for given customer
        criteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return em.createQuery(criteria)
                .getSingleResult();
    }

    @Override
    public Order save(Order order) {
        return em.merge(order);
    }

    @Override
    public void delete(Order order) {
        if(!em.contains(order)){
            order = findOrderById(order.getId(), FetchMode.FETCHED);
        }
        OrderPayment payment = order.getOrderPayment();

        payment.setOrder(null);

        em.remove(order);
    }

    @Override
    public Order submitOrder(Order cartOrder) {
        cartOrder.setOrderStatus(OrderStatus.SUBMITTED);
        return save(cartOrder);
    }

    @Override
    public Order create() {
        return ((Order) entityConfiguration.createEntityInstance("com.ffwatl.admin.order.domain.Order"));
    }

    @Override
    public Order createNewCartForCustomer(User customer, Currency currency) {
        if(customer == null) throw new UnsupportedOperationException();

        Order order = create()
                .setCustomer(customer)
                .setCurrency(currency)
                .setOrderStatus(OrderStatus.IN_PROCESS);

        order = save(order);


        return order;
    }

    @Override
    public Order findOrderByOrderNumber(String orderNumber, final FetchMode fetchMode) {
        CriteriaProperty<Order, OrderImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);
        CriteriaQuery<Order> criteria = property.getCriteria();

        Root<OrderImpl> root = property.getRoot();
        CriteriaBuilder cb = property.getBuilder();

        criteria.where(cb.equal(root.get("orderNumber"), orderNumber));
        return em.createQuery(criteria)
                .getSingleResult();
    }

    @Override
    public boolean acquireLock(Order order) {
        return false;
    }

    @Override
    public boolean releaseLock(Order order) {
        return false;
    }

    public CriteriaProperty<Order, OrderImpl> createOrderCriteriaQueryByFetchMode(final FetchMode fetchMode){
        return buildCriteriaProperty(em.getCriteriaBuilder(), fetchMode, Order.class, OrderImpl.class);
    }

    @Override
    public void addFetch(Root<OrderImpl> root) {
        Fetch<Order, OrderItem> orderItemFetch = root.fetch("orderItems", JoinType.LEFT);

        root.fetch("candidateOrderOffers", JoinType.LEFT);
        root.fetch("orderAdjustments", JoinType.LEFT);

        orderItemFetch.fetch("candidateItemOffers", JoinType.LEFT);

        orderItemFetch.fetch("orderItemPriceDetails", JoinType.LEFT)
                .fetch("orderItemPriceDetailAdjustments", JoinType.LEFT);

        orderItemFetch.fetch("orderItemQualifiers", JoinType.LEFT);
    }

}