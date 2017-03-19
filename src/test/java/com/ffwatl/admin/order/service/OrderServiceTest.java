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
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.service.UserService;
import com.ffwatl.common.persistence.FetchMode;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
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
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/dataset/product_set.xml")
public class OrderServiceTest {

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
    public void addOrderItemTest() throws AddToCartException, PricingException {
        Product product = productService.findById(1);
        User customer = userService.findById(1);
        Order order = orderService.createNewCartForCustomer(customer, Currency.UAH);
        ProductAttribute productAttribute = catalogService.findProductAttributeById(1, FetchMode.LAZY);

        OrderItemRequest request = new OrderItemRequest();
        request.setCategory(product.getCategory());
        request.setQuantity(3);
        request.setOrder(order);
        request.setItemName(product.getProductName());
        request.setProduct(product);
        request.setProductAttribute(productAttribute);

        orderService.save(order, false);

        OrderItem orderItem = orderItemService.createOrderItem(request);
        Assert.assertEquals(1, order.getId());

        orderService.addItem(1, new OrderItemRequestDTO(request), false);

        System.err.println(order);
    }

}