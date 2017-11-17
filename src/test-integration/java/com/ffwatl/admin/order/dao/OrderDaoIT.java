package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.catalog.service.ProductCategoryService;
import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.CandidateItemOffer;
import com.ffwatl.admin.offer.domain.OrderAdjustment;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.persistence.FetchMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@Named
@Rollback(value = false)
@ContextConfiguration({"/spring/application-config.xml", "/spring/spring-security.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
/*@Ignore*/
public class OrderDaoIT {

    private Order order;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCategoryService productCategoryService;

    private List<OrderItem> orderItems = new ArrayList<>();

    private Set<CandidateItemOffer> candidateItemOffers = new HashSet<>();
    private Set<OrderAdjustment> orderAdjustments = new HashSet<>();

    /*@Before
    public void start(){
        Offer offer = new OfferImpl()
                .setOfferType(OfferType.ORDER)
                .setDiscountType(OfferDiscountType.AMOUNT_OFF)
                .setEndDate(new Date())
                .setMaxUsesPerCustomer(2)
                .setName("FirstOffer")
                .setValue(20);

        offerDao.save(offer);

    }

    @Test
    *//*@Transactional(propagation = Propagation.REQUIRES_NEW)*//*
    public void before(){


        User customer = userService.findById(1);
        ProductCategory cat = productCategoryService.findById(17);

        OrderItem orderItem = new OrderItemImpl()
                .setProductCategory(cat)
                .setOrder(order);

        Offer offer = offerDao.findAllOffers().get(0);

        CandidateItemOffer candidateItemOffer = new CandidateItemOfferImpl()
                .setOffer(offer)
                .setOrderItem(orderItem)
                .setDiscountedPrice(20);

        orderAdjustments.add(new OrderAdjustmentImpl()
                .setAdjustmentValue(20)
                .setOffer(offer)
                .setOrder(order)
                .setReason("because fuck you that's why!")
        );

        candidateItemOffers.add(candidateItemOffer);

        orderItem.setCandidateItemOffers(candidateItemOffers);

        orderItems.add(orderItem);

        order = new OrderImpl()
                .setOrderNumber("FP00000001")
                .setCustomer(customer)
                .setOrderItems(orderItems)
                .setOrderAdjustments(orderAdjustments);

        orderDao.save(order);
    }*/
    @Test
    public void createOrderTest(){
        Order order = orderDao.create();
        assertThat(order, notNullValue());

        System.err.println(order);
    }

    @Test
    public void findOrderByIdFetchedTest(){
        List<OrderStatus> statuses = new ArrayList<>();
        statuses.add(OrderStatus.CONFIRMED);
        statuses.add(OrderStatus.SUBMITTED);
        List<Order> order = orderDao.findOrdersForCustomer(1, OrderStatus.PAID, FetchMode.FETCHED);
        System.err.println(order);
    }
}