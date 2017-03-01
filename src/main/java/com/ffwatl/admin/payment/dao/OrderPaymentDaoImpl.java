package com.ffwatl.admin.payment.dao;


import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.payment.domain.OrderPaymentImpl;
import com.ffwatl.common.persistence.FetchMode;
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

@Repository("order_payment_dao")
public class OrderPaymentDaoImpl implements OrderPaymentDao, FetchModeOption<OrderPayment, OrderPaymentImpl> {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;

    @Override
    public OrderPayment findPaymentById(long id, FetchMode fetchMode) throws IllegalArgumentException {
        if(id < 1) throw new IllegalArgumentException("Wrong id is given: ID = " + id);
        CriteriaProperty<OrderPayment, OrderPaymentImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);

        CriteriaQuery<OrderPayment> criteria = property.getCriteria();
        Root<OrderPaymentImpl> root = property.getRoot();

        criteria.where(property.getBuilder().equal(root.get("id"), id));

        List<OrderPayment> result = em.createQuery(criteria).getResultList();
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public OrderPayment save(OrderPayment payment) {
        return em.merge(payment);
    }

    @Override
    public List<OrderPayment> findPaymentsForOrder(Order order, FetchMode fetchMode) throws IllegalArgumentException{
        if(order == null) throw new IllegalArgumentException("Wrong argument is given: order = null");
        CriteriaProperty<OrderPayment, OrderPaymentImpl> property = createOrderCriteriaQueryByFetchMode(fetchMode);

        CriteriaQuery<OrderPayment> criteria = property.getCriteria();
        Root<OrderPaymentImpl> root = property.getRoot();
        Join<OrderPayment, Order> orderJoin = root.join("order", JoinType.INNER);

        criteria.where(property.getBuilder().equal(orderJoin.get("id"), order.getId()));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public OrderPayment create() {
        return (OrderPayment) entityConfiguration.createEntityInstance("com.ffwatl.admin.payment.domain.OrderPayment");
    }

    @Override
    public void delete(OrderPayment payment) {
        if(!em.contains(payment)){
            payment = findPaymentById(payment.getId(), FetchMode.FETCHED);
        }
        em.remove(payment);
    }

    @Override
    public CriteriaProperty<OrderPayment, OrderPaymentImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();

        final CriteriaQuery<OrderPayment> criteria = builder.createQuery(OrderPayment.class);
        final Root<OrderPaymentImpl> root = criteria.from(OrderPaymentImpl.class);

        if(fetchMode == FetchMode.FETCHED){
            Join<OrderPayment, Order> paymentOrderJoin = root.join("order", JoinType.INNER);

            paymentOrderJoin.fetch("orderItems", JoinType.LEFT);
            paymentOrderJoin.fetch("candidateOrderOffers", JoinType.LEFT);
            paymentOrderJoin.fetch("orderAdjustments", JoinType.LEFT);
        }

        criteria.select(root);
        return new CriteriaPropertyImpl<OrderPayment,OrderPaymentImpl>()
                .setCriteria(criteria)
                .setRoot(root)
                .setBuilder(builder);
    }
}