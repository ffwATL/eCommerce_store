package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderImpl;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.FetchMode;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository("order_dao")
public class OrderDaoImpl implements OrderDao{

    private static final String ORDER_LOCK_KEY = UUID.randomUUID().toString();

    @PersistenceContext
    private EntityManager em;


    @Override
    public Order findOrderById(FetchMode fetchMode, long id) {
        List<Order> orders = findOrdersByIds(fetchMode, id);
        return orders.size() > 0 ? orders.get(0) : null;
    }

    @Override
    public Order findOrderById(long id, boolean refresh) {
        Order order = findOrderById(FetchMode.FETCHED, id);

        if(refresh){
            em.refresh(order);
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByIds(FetchMode fetchMode, long... ids) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        // We only want results that match the order IDs
        criteria.where(criteria.getRoots().iterator().next().get("id")
                .as(Long.class)
                .in(ArrayUtils.toObject(ids)));

        return em.createQuery(criteria)
                .getResultList();
    }

    @Override
    public List<Order> findBatchOrders(FetchMode fetchMode, int start, int maxResults, List<OrderStatus> statuses) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        // We only want results that match the order statuses
        criteria.where(criteria.getRoots().iterator().next().get("orderStatus")
                .as(OrderStatus.class)
                .in(statuses));

        return em.createQuery(criteria)
                .setFirstResult(start)
                .setMaxResults(maxResults)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersForCustomer(FetchMode fetchMode, long customerId, OrderStatus orderStatus) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        Root<?> root = criteria.getRoots().iterator().next();
        Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

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
    public List<Order> findOrdersForCustomer(FetchMode fetchMode, long customerId) {
        return findOrdersForCustomer(fetchMode, customerId, null);
    }

    @Override
    public Order findNamedOrderForCustomer(FetchMode fetchMode, long customerId, String name) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        Root<?> root = criteria.getRoots().iterator().next();
        Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

        predicates.add(cb.equal(userJoin.get("id"), customerId));
        predicates.add(cb.equal(root.get("name"), name));

        // We only want results that match the order name for given customer
        criteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        return em.createQuery(criteria)
                .getSingleResult();
    }

    @Override
    public Order findCartForCustomer(FetchMode fetchMode, long customerId) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        Root<?> root = criteria.getRoots().iterator().next();
        Join<Order, User> userJoin = root.join("customer", JoinType.INNER);

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
        em.remove(order);
    }

    @Override
    public Order submitOrder(Order cartOrder) {
        cartOrder.setOrderStatus(OrderStatus.SUBMITTED);
        return save(cartOrder);
    }

    @Override
    public Order create() {
        return null;
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
    public Order findOrderByOrderNumber(FetchMode fetchMode, String orderNumber) {
        CriteriaQuery<Order> criteria = createOrderCriteriaQueryByFetchMode(fetchMode);

        Root<?> root = criteria.getRoots().iterator().next();
        CriteriaBuilder cb = em.getCriteriaBuilder();

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

    private CriteriaQuery<Order> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode){
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<OrderImpl> root = criteria.from(OrderImpl.class);

        if(fetchMode == FetchMode.FETCHED){
            Fetch<Order, OrderItem> orderItemFetch = root.fetch("orderItems", JoinType.LEFT);

            root.fetch("candidateOrderOffers", JoinType.LEFT);
            root.fetch("orderAdjustments", JoinType.LEFT);

            orderItemFetch.fetch("candidateItemOffers", JoinType.LEFT);

            orderItemFetch.fetch("orderItemPriceDetails", JoinType.LEFT)
                    .fetch("orderItemPriceDetailAdjustments", JoinType.LEFT);

            orderItemFetch.fetch("orderItemQualifiers", JoinType.LEFT);
        }

        criteria.select(root);
        return criteria;
    }
}