package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.order.dao.OrderDao;
import com.ffwatl.admin.order.dao.OrderItemDao;
import com.ffwatl.common.FetchMode;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@ContextConfiguration({"/spring/spring-application-context.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/data.xml")
public class OrderTest {

    private static final Logger logger = LogManager.getLogger();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Resource(name = "order_dao")
    private OrderDao orderDao;

    @Resource(name = "order_item_dao")
    private OrderItemDao orderItemDao;

    private OrderItem orderItem_1;
    private OrderItem orderItem_2;
    private OrderItem orderItem_3;
    private Order order_1;

    @Before
    public void beforeTest(){
        orderItem_1 = orderItemDao.create().setId(1);
        orderItem_2 = orderItemDao.create().setId(2);
        orderItem_3 = orderItemDao.create().setId(3);
        order_1 = orderDao.create().setId(1);

        // checking that all of created OrderItem's are not null;
        assertThat(orderItem_1, notNullValue());
        assertThat(orderItem_2, notNullValue());
        assertThat(orderItem_3, notNullValue());
        assertThat(order_1, notNullValue());
    }

    @Test
    public void findByIdTest(){
        Order order = orderDao.findOrderById(1, FetchMode.LAZY);
        assertThat(order, notNullValue());
        System.err.println(order);
    }

    @Test
    public void productCountTest(){

        assertThat(orderItem_1, notNullValue());

        logger.trace(order_1);
        logger.trace(orderItem_1);
        order_1.addOrderItem(orderItem_1);

        logger.trace(order_1);
        assertThat(order_1.getProductCount(), equalTo(1));

        order_1.removeLastOrderItem();

        logger.trace(order_1);
        assertThat(order_1.getProductCount(), equalTo(0));
    }

    @Test
    public void removeFirstOrderItemTest(){
        // adding created OrderItems to the order;
        addAllOrderItemsToTheOrder_1();

        // checking that product count equals OrderItems that we added;
        assertThat(order_1.getProductCount(), equalTo(3));

        order_1.removeFirstOrderItem();

        // checking product count after first OrderItem removed;
        assertThat(order_1.getProductCount(), equalTo(2));
        assertThat(order_1.getOrderItems().contains(orderItem_1), equalTo(false));

        order_1.removeFirstOrderItem();

        // checking product count after first OrderItem removed;
        assertThat(order_1.getProductCount(), equalTo(1));
        assertThat(order_1.getOrderItems().contains(orderItem_2), equalTo(false));

        order_1.removeFirstOrderItem();

        // checking product count after first OrderItem removed;
        // now we should have an empty OrderItem list;
        assertThatOrderHasNoOrderItems();

        // adding all OrderItems again
        addAllOrderItemsToTheOrder_1();

        // removing one by one all the OrderItems we added
        order_1.removeFirstOrderItem();
        order_1.removeFirstOrderItem();
        order_1.removeFirstOrderItem();

        // now we should have an empty list again
        assertThatOrderHasNoOrderItems();

        // an exception should be thrown if we try to remove OrderItem from empty list
        exception.expect(ArrayIndexOutOfBoundsException.class);
        order_1.removeFirstOrderItem();
    }

    @Test
    public void removeLastOrderItemTest(){
        addAllOrderItemsToTheOrder_1();

        // checking that product count equals number of OrderItems that we added;
        assertThat(order_1.getProductCount(), equalTo(3));

        order_1.removeLastOrderItem();

        // checking product count after the last OrderItem removed;
        assertThat(order_1.getProductCount(), equalTo(2));
        assertThat(order_1.getOrderItems().contains(orderItem_3), equalTo(false));

        order_1.removeLastOrderItem();

        // checking product count after the last OrderItem removed;
        assertThat(order_1.getProductCount(), equalTo(1));
        assertThat(order_1.getOrderItems().contains(orderItem_2), equalTo(false));

        order_1.removeLastOrderItem();

        // checking product count after the last OrderItem removed;
        assertThatOrderHasNoOrderItems();

        // adding all OrderItems again
        addAllOrderItemsToTheOrder_1();

        // removing one by one last of the OrderItems we added
        order_1.removeLastOrderItem();
        order_1.removeLastOrderItem();
        order_1.removeLastOrderItem();

        // now we should have an empty list again
        assertThatOrderHasNoOrderItems();

        // an exception should be thrown if we try to remove OrderItem from empty list
        exception.expect(ArrayIndexOutOfBoundsException.class);
        order_1.removeLastOrderItem();
    }

    @Test
    public void removeOrderItemTest(){
        // adding created OrderItems to the order;
        addAllOrderItemsToTheOrder_1();

        order_1.removeOrderItem(1);

        // checking product count after the OrderItem with index=2 removed;
        assertThat(order_1.getProductCount(), equalTo(2));
        assertThat(order_1.getOrderItems().contains(orderItem_2), equalTo(false));

        order_1.removeOrderItem(0);

        // checking product count after the OrderItem with index=0 removed;
        assertThat(order_1.getProductCount(), equalTo(1));
        assertThat(order_1.getOrderItems().contains(orderItem_1), equalTo(false));

        order_1.removeOrderItem(0);

        // now we should have an empty list
        assertThatOrderHasNoOrderItems();

        // adding created OrderItems to the order;
        addAllOrderItemsToTheOrder_1();

        order_1.removeOrderItem(orderItem_2);

        // checking product count after the OrderItem orderItem_2 removed;
        assertThat(order_1.getProductCount(), equalTo(2));
        assertThat(order_1.getOrderItems().contains(orderItem_2), equalTo(false));

        order_1.removeOrderItem(orderItem_1);

        // checking product count after the OrderItem orderItem_1 removed;
        assertThat(order_1.getProductCount(), equalTo(1));
        assertThat(order_1.getOrderItems().contains(orderItem_1), equalTo(false));

        order_1.removeOrderItem(orderItem_3);

        // now we should have an empty list
        assertThatOrderHasNoOrderItems();

        order_1.addOrderItem(orderItemDao.create().setId(111));


        // an exception shouldn't be thrown if we try to remove OrderItem that doesn't in list
        order_1.removeOrderItem(orderItem_3);
    }

    private void assertThatOrderHasNoOrderItems(){
        assertThat(order_1.getProductCount(), equalTo(0));
        assertThat(order_1.getOrderItems().contains(orderItem_1), equalTo(false));
        assertThat(order_1.getOrderItems().contains(orderItem_2), equalTo(false));
        assertThat(order_1.getOrderItems().contains(orderItem_3), equalTo(false));
    }

    /**
     * Adding all the OrderItems from global variables to the {@link #order_1}
     */
    private void addAllOrderItemsToTheOrder_1(){
        // adding created OrderItems to the order;
        order_1
                .addOrderItem(orderItem_1)
                .addOrderItem(orderItem_2)
                .addOrderItem(orderItem_3);
    }
}