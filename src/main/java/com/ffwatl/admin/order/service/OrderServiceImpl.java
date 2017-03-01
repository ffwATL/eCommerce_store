package com.ffwatl.admin.order.service;

import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferService;
import com.ffwatl.admin.order.dao.OrderDao;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.payment.dao.OrderPaymentDao;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.payment.domain.secure.Referenced;
import com.ffwatl.admin.payment.service.SecureOrderPaymentService;
import com.ffwatl.admin.pricing.PricingService;
import com.ffwatl.admin.user.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("order_service")
public class OrderServiceImpl implements OrderService {

    /* DAOs */
    @Resource(name = "order_payment_dao")
    private OrderPaymentDao orderPaymentDao;

    @Resource(name = "order_dao")
    private OrderDao orderDao;

    @Resource(name = "offer_dao")
    private OfferDao offerDao;

    /* Services */
    @Resource(name = "pricing_service")
    private PricingService pricingService;

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Resource(name = "fulfillment_group_service")
    private FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "offer_service")
    private OfferService offerService;

    @Resource(name = "secure_order_payment_service")
    private SecureOrderPaymentService secureOrderPaymentService;




    @Override
    public Order createNewCartForCustomer(User customer) {
        return null;
    }

    @Override
    public Order createNamedOrderForCustomer(String name, User customer) {
        return null;
    }

    @Override
    public Order findNamedOrderForCustomer(String name, User customer) {
        return null;
    }

    @Override
    public Order findOrderById(long orderId) {
        return null;
    }

    @Override
    public Order findOrderById(long orderId, boolean refresh) {
        return null;
    }

    @Override
    public Order findCartForCustomer(User customer) {
        return null;
    }

    @Override
    public List<Order> findOrdersForCustomer(User customer) {
        return null;
    }

    @Override
    public List<Order> findOrdersForCustomer(User customer, OrderStatus status) {
        return null;
    }

    @Override
    public Order findOrderByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public List<OrderPayment> findPaymentsForOrder(Order order) {
        return null;
    }

    @Override
    public OrderPayment addPaymentToOrder(Order order, OrderPayment payment, Referenced securePaymentInfo) {
        return null;
    }

    @Override
    public Order save(Order order, Boolean priceOrder) {
        return null;
    }

    @Override
    public Order save(Order order, boolean priceOrder, boolean repriceItems) {
        return null;
    }

    @Override
    public void cancelOrder(Order order) {

    }

    @Override
    public Order addOfferCode(Order order, OfferCode offerCode, boolean priceOrder) {
        return null;
    }

    @Override
    public Order removeOfferCode(Order order, OfferCode offerCode, boolean priceOrder) {
        return null;
    }

    @Override
    public Order removeAllOfferCodes(Order order, boolean priceOrder) {
        return null;
    }

    @Override
    public Order getNullOrder() {
        return null;
    }

    @Override
    public Order confirmOrder(Order order) {
        return null;
    }

    @Override
    public Order addItem(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) {
        return null;
    }

    @Override
    public Order addItemWithPriceOverrides(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) {
        return null;
    }

    @Override
    public Order updateItemQuantity(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) {
        return null;
    }

    @Override
    public Order removeItem(long orderId, long orderItemId, boolean priceOrder) {
        return null;
    }

    @Override
    public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, boolean priceOrder) {
        return null;
    }

    @Override
    public Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder) {
        return null;
    }

    @Override
    public void removePaymentFromOrder(Order order, OrderPayment paymentInfo) {

    }

    @Override
    public void deleteOrder(Order cart) {

    }

    @Override
    public Order removeInactiveItems(long orderId, boolean priceOrder) {
        return null;
    }

    @Override
    public Order updateProductOptionsForItem(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) {
        return null;
    }

    @Override
    public void preValidateCartOperation(Order cart) {

    }

    @Override
    public void preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto) {

    }

    @Override
    public boolean acquireLock(Order order) {
        return false;
    }

    @Override
    public boolean releaseLock(Order order) {
        return false;
    }
}