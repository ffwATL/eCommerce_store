package com.ffwatl.admin.order.service;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.catalog.service.ProductService;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequest;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.exception.AddToCartException;
import com.ffwatl.admin.order.service.exception.RemoveFromCartException;
import com.ffwatl.admin.order.service.exception.UpdateCartException;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.schedule.service.SingleTimeTimerTaskService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@ContextConfiguration({"/spring/spring-application-context.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@DatabaseSetup("/dataset/product_set.xml")
public class OrderServiceIntegrationTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProductService productService;

    @Resource(name = "orderNumberGenerator")
    private OrderNumberGenerator orderNumberGenerator;

    @Resource(name = "order_service")
    private OrderService orderService;

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Resource(name = "user_service")
    private UserService userService;

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService taskService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before(){
        taskService.clearAllTasks();
    }


    @Test
    @Transactional
    public void datasetTest(){
        List list = productService.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());

        System.err.println(list);
    }

    @Test
    public void orderNumberGeneratorTest() {
        Assert.assertEquals(1000, orderNumberGenerator.getCounter());
        Assert.assertEquals(1001, orderNumberGenerator.getCounter());
    }

    @Test
    public void decrementInventoryNormalTest() throws PricingException, AddToCartException, InterruptedException {
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        int requestedQuantity = 3;
        boolean incrementOrderItemQuantity = true;

        int expectedQuantity = 0;

        inventoryQuantityTest(productId, customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, expectedQuantity);

    }

    @Test
    public void decrementInventoryUnavailableTest() throws AddToCartException, PricingException {
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 2;
        int requestedQuantity = 4;
        boolean incrementOrderItemQuantity = true;
        int expectedQuantity = 2;

        taskService.clearAllTasks();

        Exception e = new Exception();

        try{
            inventoryQuantityTest(productId, customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, expectedQuantity);
        }catch (AddToCartException ex){
            e = ex;
            ProductAttribute attribute = catalogService.findProductAttributeById(productAttributeId, FetchMode.LAZY);
            Assert.assertEquals(expectedQuantity, attribute.getQuantity());

            Assert.assertTrue(taskService.getPendingTasksSize() == 0);
        }

        Assert.assertTrue(e instanceof AddToCartException);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void orderSingleTimeTimerTaskTest() throws AddToCartException, PricingException, InterruptedException {
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        int requestedQuantity = 3;
        boolean incrementOrderItemQuantity = true;
        long delayMilliseconds = 30000;

        int expectedQuantity = 0;

        inventoryQuantityTest(productId, customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, expectedQuantity);
        LOGGER.info("Waiting for a scheduler. {} seconds remaining..", (delayMilliseconds / 1000));
        Thread.sleep(delayMilliseconds);
    }

    @Test
    @Transactional(propagation =Propagation.NOT_SUPPORTED )
    public void orderAddItemTimerTaskTest() throws InterruptedException, PricingException, AddToCartException {
        long orderId = 1;
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        long productAttribute_2Id = 3;
        int requestedQuantity_1 = 2;
        int requestedQuantity_2 = 3;
        boolean incrementOrderItemQuantity = true;
        long delayMilliseconds = 30000;

        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        handleAddOrderItemRequest(orderItemRequest);

        Thread.sleep(10000);

        LOGGER.info("########### 10 seconds passed");
        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttribute_2Id, requestedQuantity_2, incrementOrderItemQuantity, orderItemRequest.getOrder().getId());
        handleAddOrderItemRequest(orderItemRequest);
        LOGGER.info("########### request handled");

        Thread.sleep(delayMilliseconds);
    }

    @Test
    @Transactional
    public void removeOrderItemFromCartTest() throws AddToCartException, PricingException, RemoveFromCartException, InterruptedException {
        long orderId = 1;
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        long productAttribute_2Id = 3;
        int requestedQuantity_1 = 2;
        int requestedQuantity_2 = 3;
        boolean incrementOrderItemQuantity = true;
        int expectedQuantity = 3;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        // check if the operation succeeded
        checkProductAttributeQuantity(productAttributeId, 1);

        // create OrderItemRequest for the second productAttribute
        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttribute_2Id, requestedQuantity_2, incrementOrderItemQuantity, orderItemRequest.getOrder().getId());
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        LOGGER.info("2 order item added. Waiting 5 sec..");
        Thread.sleep(5000);

        OrderItem orderItem = orderItemRequest.getOrder().getOrderItems().get(0);

        Assert.assertNotNull(orderItem);
        ProductAttribute productAttribute = orderItem.getProductAttribute();
        Assert.assertNotNull(productAttribute);

        LOGGER.info("Removing order item with ID = {}", orderItem.getId());

        // to test remove behavior we need to change flag 'IncrementOrderItemQuantity'
        // in our OrderItemRequest to 'false' state.
        orderItemRequest.setIncrementOrderItemQuantity(false);

        // execute remove OrderItem operation for the first OrderItem we added to the order.
        handleRemoveOrderItemRequest(orderItemRequest, orderItem.getId());
        LOGGER.info("Removed successfully!");

        // finally we need to check whether the operation was canceled and ProductAttribute quantity rolled back
        checkProductAttributeQuantity(productAttribute.getId(), expectedQuantity);
    }

    @Test
    @Transactional
    public void addTheSameItemRepeatablePositiveCaseTest() throws AddToCartException, PricingException {
        long orderId = 1;
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        int requestedQuantity = 2;

        boolean incrementOrderItemQuantity = true;
        int expectedQuantity = 1;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        OrderItem orderItem = orderItemRequest.getOrder().getOrderItems().get(0);

        Assert.assertNotNull(orderItem);
        ProductAttribute productAttribute = orderItem.getProductAttribute();
        Assert.assertNotNull(productAttribute);

        checkProductAttributeQuantity(productAttribute.getId(), expectedQuantity);

        // we need to refresh order ID because it may be different than initial
        orderId = orderItemRequest.getOrder().getId();
        requestedQuantity = 1;
        expectedQuantity = 0;

        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        checkProductAttributeQuantity(productAttribute.getId(), expectedQuantity);
        System.err.println("Order total: " + orderItemRequest.getOrder().getTotal());
    }

    @Test
    @Transactional
    public void decrementItemQuantityPositiveCaseTest() throws AddToCartException, PricingException, UpdateCartException, RemoveFromCartException {
        long orderId = 0;
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        int requestedQuantity_1 = 2;

        boolean incrementOrderItemQuantity = true;
        int expectedQuantity = 1;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        OrderItem orderItem = orderItemRequest.getOrder().getOrderItems().get(0);

        Assert.assertNotNull(orderItem);
        ProductAttribute productAttribute = orderItem.getProductAttribute();
        Assert.assertNotNull(productAttribute);

        checkProductAttributeQuantity(productAttribute.getId(), expectedQuantity);

        // we need to refresh order ID because it may be different than initial
        orderId = orderItemRequest.getOrder().getId();
        requestedQuantity_1 = 1;
        incrementOrderItemQuantity = false;
        expectedQuantity = 2;

        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        handleUpdateOrderItemQuantityRequest(orderItemRequest, orderItem.getId());

        checkProductAttributeQuantity(productAttributeId, expectedQuantity);

        requestedQuantity_1 = 1;
        incrementOrderItemQuantity = false;
        expectedQuantity = 3;

        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        handleUpdateOrderItemQuantityRequest(orderItemRequest, orderItem.getId());

        checkProductAttributeQuantity(productAttributeId, expectedQuantity);
    }

    @Test
    @Transactional
    public void cancelOrderPositiveCaseTest() throws AddToCartException, PricingException, InterruptedException {
        long orderId = 1;
        long productId = 1;
        long customerId = 1;
        long productAttributeId_1 = 1;
        long productAttributeId_2 = 3;
        int requestedQuantity_1 = 2;
        int requestedQuantity_2 = 3;
        boolean incrementOrderItemQuantity = true;
        int expectedQuantity = 3;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId_1, requestedQuantity_1, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        // create OrderItemRequest for the second productAttribute
        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId_2, requestedQuantity_2, incrementOrderItemQuantity, orderItemRequest.getOrder().getId());
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        handleCancelOrderRequest(orderItemRequest);

        checkProductAttributeQuantity(productAttributeId_1, expectedQuantity);
        checkProductAttributeQuantity(productAttributeId_2, 5);
    }

    @Test
    public void removeOrderItemThroughUpdateQtyTest() throws PricingException, AddToCartException, InterruptedException {
        long orderId = 0;
        long productId = 1;
        long customerId = 1;
        long productAttributeId_1 = 1;
        long productAttributeId_2 = 3;
        int requestedQty_1 = 2;
        int requestedQty_2 = 1;
        boolean incrementOrderItemQuantity = true;
        int expectedOrderItemsSize = 1;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        OrderItemRequest orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId_1, requestedQty_1, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        // create OrderItemRequest for the second productAttribute
        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId_2, requestedQty_2, incrementOrderItemQuantity, orderItemRequest.getOrder().getId());
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        incrementOrderItemQuantity = false;

        // create OrderItemRequest for the first productAttribute. It creates a new order for
        // the given customer.
        orderItemRequest = buildOrderItemRequestForOrderId(productId,
                customerId, productAttributeId_1, requestedQty_1, incrementOrderItemQuantity, orderId);
        // execute "Add order item" for the above request
        handleAddOrderItemRequest(orderItemRequest);

        Order order = findOrderById(orderItemRequest.getOrder().getId(), userService.findById(customerId));

        Assert.assertEquals(expectedOrderItemsSize, order.getOrderItems().size());
    }

    private void checkProductAttributeQuantity(long attrId, int expectedQuantity){
        ProductAttribute attribute = catalogService.findProductAttributeById(attrId, FetchMode.LAZY);
        Assert.assertEquals(expectedQuantity, attribute.getQuantity());
        LOGGER.info("Assert passed! Expected quantity {}, actual quantity {}", expectedQuantity, attribute.getQuantity());
    }

    private Order findOrderById(long id, User customer){
        Order order;
        try {
            order = orderService.findOrderById(id, FetchMode.FETCHED);
            Assert.assertNotNull(order);
        }catch (AssertionError e){
            order = orderService.createNewCartForCustomer(customer, Currency.UAH);
        }
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private OrderItemRequest buildOrderItemRequestForOrderId(long productId, long customerId,
                                                             long attrId, int requestedQuantity,
                                                             boolean incrementOrderItemQuantity,
                                                             long orderId) throws PricingException {
        Product product = productService.findById(productId);
        User customer = userService.findById(customerId);
        Order order = findOrderById(orderId, customer);

        ProductAttribute productAttribute = catalogService.findProductAttributeById(attrId, FetchMode.LAZY);

        OrderItemRequest request = new OrderItemRequest();
        request.setCategory(product.getCategory());
        request.setQuantity(requestedQuantity);
        request.setOrder(order);
        request.setItemName(product.getProductName());
        request.setProduct(product);
        request.setProductAttribute(productAttribute);
        request.setColor(product.getColor());
        request.setRetailPriceOverride(product.getRetailPrice());
        request.setSalePriceOverride(product.getSalePrice());
        request.setIncrementOrderItemQuantity(incrementOrderItemQuantity);

        return request;
    }

    /*@Transactional(propagation = Propagation.NOT_SUPPORTED)*/
    private OrderItemRequest inventoryQuantityTest(long productId, long customerId,
                                                   long attrId, int requestedQuantity,
                                                   boolean incrementOrderItemQuantity, int expectedQuantity) throws AddToCartException, PricingException {

        OrderItemRequest request = buildOrderItemRequestForOrderId(productId, customerId, attrId,
                requestedQuantity, incrementOrderItemQuantity, 1);

        handleAddOrderItemRequest(request);
        ProductAttribute attribute = catalogService.findProductAttributeById(attrId, FetchMode.LAZY);

        Assert.assertEquals(expectedQuantity, attribute.getQuantity());
        return request;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void handleAddOrderItemRequest(OrderItemRequest request) throws AddToCartException {
        orderService.addItem(request.getOrder().getId(), new OrderItemRequestDTO(request), true);
    }

    private void handleRemoveOrderItemRequest(OrderItemRequest request, long orderItemId) throws RemoveFromCartException {
        orderService.removeItem(request.getOrder().getId(), orderItemId, true);
    }

    private void handleUpdateOrderItemQuantityRequest(OrderItemRequest request, long orderItemId) throws RemoveFromCartException, UpdateCartException {
        orderService.updateItemQuantity(request.getOrder().getId(), new OrderItemRequestDTO(request).setOrderItemId(orderItemId), true);
    }

    private void handleCancelOrderRequest(OrderItemRequest request){
        orderService.cancelOrder(request.getOrder(), true);
    }

}