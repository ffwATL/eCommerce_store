package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Rollback(value = false)
@DirtiesContext
@ContextConfiguration({"/spring/application-config.xml", "/spring/spring-security.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderItemDaoTest {

    @Resource(name = "order_item_dao")
    private OrderItemDao orderItemDao;

    @Test
    public void createOrderItemTest(){
        OrderItem orderItem = orderItemDao.create();
        assertThat(orderItem, notNullValue());
        assertThat(orderItem.getId(), Matchers.equalTo(0L));

        // all the collection should be not null and should be empty by the default
        assertThat(orderItem.getOrderItemQualifiers(), notNullValue());
        assertThat(orderItem.getOrderItemQualifiers(), empty());

        assertThat(orderItem.getOrderItemPriceDetails(), notNullValue());
        assertThat(orderItem.getOrderItemPriceDetails(), empty());

        assertThat(orderItem.getCandidateItemOffers(), notNullValue());
        assertThat(orderItem.getCandidateItemOffers(), empty());

        // all the nested objects should be null by the default
        assertThat(orderItem.getCategory(), nullValue());
        assertThat(orderItem.getOrder(), nullValue());

        assertThat(orderItem.getOrderItemColor(), nullValue());

        assertThat(orderItem.getDiscountValue(), Matchers.equalTo(0));
    }

    @Test
    public void createOrderItemQualifierTest(){
        OrderItemQualifier orderItemQualifier = orderItemDao.createOrderItemQualifier();

        assertThat(orderItemQualifier, notNullValue());
        assertThat(orderItemQualifier.getId(), Matchers.equalTo(0L));

        // all the nested objects should be null by default
        assertThat(orderItemQualifier.getOffer(), nullValue());
        assertThat(orderItemQualifier.getOrderItem(), nullValue());
    }

    @Test
    public void createOrderItemPriceDetailTest(){
        OrderItemPriceDetail orderItemPriceDetail = orderItemDao.createOrderItemPriceDetail();
        assertThat(orderItemPriceDetail, notNullValue());
        assertThat(orderItemPriceDetail.getId(), equalTo(0L));

        // all the collection should be not null and should be empty by the default
        assertThat(orderItemPriceDetail.getOrderItemPriceDetailAdjustments(), notNullValue());
        assertThat(orderItemPriceDetail.getOrderItemPriceDetailAdjustments(), empty());

        // all the nested objects should be null by default
        assertThat(orderItemPriceDetail.getOrderItem(), nullValue());
        assertThat(orderItemPriceDetail.getCurrency(), nullValue());
    }

    @Test
    public void initializeOrderItemPriceDetailsTest(){
        OrderItem orderItem = orderItemDao.create()
                .setId(5)
                .setQuantity(2);

        OrderItemPriceDetail detail = orderItemDao.initializeOrderItemPriceDetails(orderItem);

        assertThat(detail.getQuantity(), equalTo(2));
        assertThat(detail.getOrderItem(), notNullValue());
        assertThat(detail.getOrderItem().getId(), equalTo(5L));
        assertThat(detail.getOrderItem().getQuantity(), equalTo(2));
    }
}