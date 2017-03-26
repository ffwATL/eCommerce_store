package com.ffwatl.admin.order.service;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.catalog.service.ProductService;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.call.OrderItemRequest;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.exception.AddToCartException;
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
@ContextConfiguration({"/spring/scheduler-config.xml"})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@DatabaseSetup("/dataset/product_set.xml")
public class OrderServiceTest {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProductService productService;

    @Resource(name = "orderNumberGenerator")
    private OrderNumberGenerator orderNumberGenerator;

    @Resource(name = "order_service")
    private OrderService orderService;

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Resource(name = "user_service")
    private UserService userService;

    @Resource(name = "order_single_time_timer_task_service")
    private SingleTimeTimerTaskService taskService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
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

        Exception e = new Exception();

        try{
            inventoryQuantityTest(productId, customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, expectedQuantity);
        }catch (AddToCartException ex){
            e = ex;
            ProductAttribute attribute = catalogService.findProductAttributeById(productAttributeId, FetchMode.LAZY);
            Assert.assertEquals(expectedQuantity, attribute.getQuantity());
            Assert.assertEquals(0, taskService.getPendingTasksSize());
        }

        Assert.assertTrue(e instanceof AddToCartException);
        System.err.println(orderService.findOrderById(1, FetchMode.FETCHED));

    }

    @Test
    @Transactional(propagation =Propagation.NOT_SUPPORTED )
    public void orderSingleTimeTimerTaskTest() throws AddToCartException, PricingException, InterruptedException {
        long productId = 1;
        long customerId = 1;
        long productAttributeId = 1;
        int requestedQuantity = 3;
        boolean incrementOrderItemQuantity = true;
        long delayMilliseconds = 30000;

        int expectedQuantity = 3;

        inventoryQuantityTest(productId, customerId, productAttributeId, requestedQuantity, incrementOrderItemQuantity, expectedQuantity);
        LOGGER.info("Waiting for a scheduler. {} seconds remaining..", (delayMilliseconds / 1000));
        Thread.sleep(delayMilliseconds);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private OrderItemRequest buildOrderItemRequestWithNewOrder(long productId, long customerId,
                                                               long attrId, int requestedQuantity,
                                                               boolean incrementOrderItemQuantity) throws PricingException {
        Product product = productService.findById(productId);
        User customer = userService.findById(customerId);
        Order order = orderService.createNewCartForCustomer(customer, Currency.UAH);
        ProductAttribute productAttribute = catalogService.findProductAttributeById(attrId, FetchMode.LAZY);

        /*order = orderService.save(order, false);*/

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

    @Transactional
    private OrderItemRequest inventoryQuantityTest(long productId, long customerId,
                                                   long attrId, int requestedQuantity,
                                                   boolean incrementOrderItemQuantity, int expectedQuantity) throws AddToCartException, PricingException {

        OrderItemRequest request = buildOrderItemRequestWithNewOrder(productId, customerId, attrId,
                requestedQuantity, incrementOrderItemQuantity);

        orderService.addItem(request.getOrder().getId(), new OrderItemRequestDTO(request), false);
        ProductAttribute attribute = catalogService.findProductAttributeById(attrId, FetchMode.LAZY);

        Assert.assertEquals(expectedQuantity, attribute.getQuantity());
        return request;

    }

}