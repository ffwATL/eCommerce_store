package com.ffwatl.admin.order.service;

import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.service.CatalogService;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.OfferService;
import com.ffwatl.admin.order.dao.OrderDao;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.exception.*;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.payment.dao.OrderPaymentDao;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.payment.domain.secure.Referenced;
import com.ffwatl.admin.payment.service.SecureOrderPaymentService;
import com.ffwatl.admin.pricing.PricingService;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.workflow.ProcessContext;
import com.ffwatl.admin.workflow.Processor;
import com.ffwatl.admin.workflow.WorkflowException;
import com.ffwatl.common.extension.ExtensionResultHolder;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.schedule.SingleTimeTimerTask;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service("order_service")
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger();

    /* DAOs */
    @Resource(name = "order_payment_dao")
    private OrderPaymentDao orderPaymentDao;

    @Resource(name = "order_dao")
    private OrderDao orderDao;

    @Resource(name = "catalog_service")
    private CatalogService catalogService;

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

    @Resource(name = "order_service_extension_manager")
    private OrderServiceExtensionManager extensionManager;

    /* Workflows*/
    @Resource(name = "addItemWorkflow")
    private Processor addItemWorkflow;

    @Resource(name = "updateItemWorkflow")
    private Processor updateItemWorkflow;

    @Resource(name = "removeItemWorkflow")
    private Processor removeItemWorkflow;

    private int pricingRetryCountForLockFailure = 3;

    private long pricingRetryWaitIntervalForLockFailure = 500L;

    /*Fields*/
    private boolean moveNamedOrderItems = true;
    private boolean deleteEmptyNamedOrders = true;
    private static final String ORDER_OBJECT = "Order";
    private static final String CUSTOMER_OBJECT = "Customer";
    private static final String CART_OBJECT = "Cart";
    private static final String ORDER_ITEM = "OrderItem";
    private static final String PRODUCT_ATTR = "ProductAttribute";

    @Override
    @Transactional
    public Order createNewCartForCustomer(User customer, Currency currency) {
        return orderDao.createNewCartForCustomer(customer, currency);
    }

    @Override
    @Transactional
    public Order createNamedOrderForCustomer(String name, User customer) {
        Order namedOrder = orderDao.create();
        namedOrder.setCustomer(customer);
        namedOrder.setName(name);
        namedOrder.setOrderStatus(OrderStatus.NAMED);

        if (extensionManager != null) {
            extensionManager.getProxy().attachAdditionalDataToNewNamedCart(customer, namedOrder);
        }

        return persist(namedOrder); // No need to price here
    }

    // This method exists to provide OrderService methods the ability to save an order
    // without having to worry about a PricingException being thrown.
    private Order persist(Order order) {
        return orderDao.save(order);
    }

    private void checkValueIsNotNull(Object object, String parameterName){
        if(object == null) {
            throw new IllegalArgumentException("Incorrect "+ parameterName +" parameter is given: null");
        }
    }

    @Override
    public Order findNamedOrderForCustomer(String name, User customer, FetchMode fetchMode) {
        checkValueIsNotNull(customer, CUSTOMER_OBJECT);
        return orderDao.findNamedOrderForCustomer(customer.getId(), name, fetchMode);
    }

    @Override
    public Order findOrderById(long orderId, FetchMode fetchMode) {
        return orderDao.findOrderById(orderId, fetchMode);
    }

    @Override
    public Order findOrderById(long orderId, boolean refresh) {
        return orderDao.findOrderById(orderId, refresh);
    }

    @Override
    public Order findCartForCustomer(User customer, FetchMode fetchMode) {
        checkValueIsNotNull(customer, CUSTOMER_OBJECT);
        return orderDao.findCartForCustomer(customer.getId(), fetchMode);
    }

    @Override
    public List<Order> findOrdersForCustomer(User customer, FetchMode fetchMode) {
        checkValueIsNotNull(customer, CUSTOMER_OBJECT);
        return orderDao.findOrdersForCustomer(customer.getId(), fetchMode);
    }

    @Override
    public List<Order> findOrdersForCustomer(User customer, OrderStatus status, FetchMode fetchMode) {
        checkValueIsNotNull(customer, CUSTOMER_OBJECT);
        return orderDao.findOrdersForCustomer(customer.getId(), status, fetchMode);
    }

    @Override
    public Order findOrderByOrderNumber(String orderNumber, FetchMode fetchMode) {
        return orderDao.findOrderByOrderNumber(orderNumber, fetchMode);
    }

    @Override
    public List<OrderPayment> findPaymentsForOrder(Order order, FetchMode fetchMode) {
        return orderPaymentDao.findPaymentsForOrder(order, fetchMode);
    }

    @Override
    @Transactional
    public OrderPayment addPaymentToOrder(Order order, OrderPayment payment, Referenced securePaymentInfo) {
        checkValueIsNotNull(payment, "Payment");
        payment.setOrder(order);
        order.setOrderPayment(payment);
        order = persist(order);

        if (securePaymentInfo != null) {
            secureOrderPaymentService.save(securePaymentInfo);
        }

        return order.getOrderPayment();
    }

    @Override
    @Transactional
    public Order save(Order order, boolean priceOrder) throws PricingException {
        try {
            order = persist(order);
        } catch (RuntimeException ex) {
            logger.error("Exception is occurred while saving order. " + ex.getMessage());
            throw ex;
        }
        //make any pricing changes - possibly retrying with the persisted state if there's a lock failure
        if (priceOrder) {
            int retryCount = 0;
            boolean isValid = false;

            while (!isValid) {
                try {
                    order = pricingService.executePricing(order);
                    isValid = true;
                } catch (Exception ex) {
                    boolean isValidCause = false;
                    Throwable cause = ex;

                    while (!isValidCause) {
                        if (cause.getClass().equals(LockAcquisitionException.class)) {
                            isValidCause = true;
                        }
                        cause = cause.getCause();
                        if (cause == null) {
                            break;
                        }
                    }

                    if (isValidCause) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Problem acquiring lock during pricing call - attempting to price again.");
                        }
                        isValid = false;
                        if (retryCount >= pricingRetryCountForLockFailure) {
                            if (logger.isInfoEnabled()) {
                                logger.info("Problem acquiring lock during pricing call. Retry limit exceeded at (" + retryCount + "). Throwing exception.");
                            }
                            if (ex instanceof PricingException) {
                                throw (PricingException) ex;
                            } else {
                                throw new PricingException(ex);
                            }
                        } else {
                            order = findOrderById(order.getId(), FetchMode.FETCHED);
                            retryCount++;
                        }
                        try {
                            Thread.sleep(pricingRetryWaitIntervalForLockFailure);
                        } catch (Throwable e) {
                            //do nothing
                        }
                    } else {
                        /*if (ex instanceof PricingException) {
                            throw (PricingException) ex;
                        } else {
                            throw new PricingException(ex);
                        }*/
                        throw ex;
                    }
                }
            }

            try {
                order = persist(order);
                if (extensionManager != null) {
                    extensionManager.getProxy().attachAdditionalDataToOrder(order, true);
                }
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
                throw ex;
            }
        }
        return order;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    private void rollbackAllOrderItemsForOrder(Order order){
        for(OrderItem orderItem: order.getOrderItems()){
            ProductAttribute orderedAttribute = orderItem.getProductAttribute();
            ProductAttribute attribute = catalogService.findProductAttributeById(orderedAttribute.getId(), FetchMode.LAZY);
            attribute.setQuantity(attribute.getQuantity() + orderedAttribute.getQuantity());
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    private void rollbackOrderItemForOrder(OrderItem orderItem) {
        checkValueIsNotNull(orderItem, ORDER_ITEM);
        long productAttrId = orderItem.getProductAttribute().getId();

        ProductAttribute productAttribute = catalogService.findProductAttributeById(productAttrId, FetchMode.LAZY);
        checkValueIsNotNull(productAttribute, PRODUCT_ATTR);

        int orderedQty = orderItem.getQuantity();
        int remainingQty = productAttribute.getQuantity();

        productAttribute.setQuantity(orderedQty + remainingQty);
        orderItemService.delete(orderItem);
    }

    @Override
    @Transactional
    public Order save(Order order, boolean priceOrder, boolean repriceItems) throws PricingException {
        return save(order, priceOrder);
    }

    @Override
    @Transactional
    public void cancelOrder(Order order) {
        checkValueIsNotNull(order, ORDER_OBJECT);
        if(order.getOrderStatus() != OrderStatus.NAMED){
            rollbackAllOrderItemsForOrder(order);
        }
        orderDao.delete(order);
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
    @Transactional
    public Order confirmOrder(Order order) {
        return orderDao.submitOrder(order);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order addItem(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException {
        // Don't allow overrides from this method.
        orderItemRequestDTO.setOverrideRetailPrice(0);
        orderItemRequestDTO.setOverrideSalePrice(0);
        return addItemWithPriceOverrides(orderId, orderItemRequestDTO, priceOrder);
    }
    private boolean itemMatches(long productId, ProductAttribute productAttribute, OrderItemRequestDTO item2) {
        // Must match on id and attribute
        if (productId > 0 && item2 != null) {
            if (productId == item2.getProductId()) {
                return compareAttributes(productAttribute, item2.getProductAttribute());
            }
        }
        return false;
    }

    private boolean compareAttributes(ProductAttribute item1Attribute, ProductAttribute item2Attribute){
        if(item1Attribute != null && item2Attribute != null) {
            return item1Attribute.getId() == item2Attribute.getId();
        }
        return false;
    }

    private OrderItem findMatchingItem(Order order, OrderItemRequestDTO itemToFind) {
        if (order == null) {
            return null;
        }
        for (OrderItem currentItem : order.getOrderItems()) {
            if (itemMatches(currentItem.getProductId(), currentItem.getProductAttribute(), itemToFind)) {
                return currentItem;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order addItemWithPriceOverrides(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException {
        Order order = findOrderById(orderId, FetchMode.FETCHED);
        preValidateCartOperation(order);

        OrderItem item = findMatchingItem(order, orderItemRequestDTO);
        if (item != null) {
            orderItemRequestDTO.setQuantity(item.getQuantity() + orderItemRequestDTO.getQuantity());
            orderItemRequestDTO.setOrderItemId(item.getId());
            try {
                return updateItemQuantity(orderId, orderItemRequestDTO, priceOrder);
            } catch (RemoveFromCartException e) {
                throw new AddToCartException("Unexpected error - system tried to remove item while adding to cart", e);
            } catch (UpdateCartException e) {
                throw new AddToCartException("Could not update quantity for matched item", e);
            }
        }

        try {
            // We only want to price on the last addition for performance reasons and only if the user asked for it.
            int numAdditionRequests = priceOrder ? (1 + orderItemRequestDTO.getChildOrderItems().size()) : -1;
            int currentAddition = 1;

            CartOperationRequest cartOpRequest = new CartOperationRequest(order,
                    orderItemRequestDTO, currentAddition == numAdditionRequests);
            ProcessContext<CartOperationRequest> context = (ProcessContext<CartOperationRequest>) addItemWorkflow.doActivities(cartOpRequest);

            /*List<ActivityMessageDTO> orderMessages = new ArrayList<ActivityMessageDTO>();
            orderMessages.addAll(((ActivityMessages) context).getActivityMessages());*/

            if (CollectionUtils.isNotEmpty(orderItemRequestDTO.getChildOrderItems())) {
                for (OrderItemRequestDTO childRequest : orderItemRequestDTO.getChildOrderItems()) {
                    childRequest.setParentOrderItemId(context.getSeedData().getOrderItem().getId());
                    currentAddition++;

                    CartOperationRequest childCartOpRequest = new CartOperationRequest(context.getSeedData().getOrder(), childRequest, currentAddition == numAdditionRequests);
                    ProcessContext<CartOperationRequest> childContext = (ProcessContext<CartOperationRequest>) addItemWorkflow.doActivities(childCartOpRequest);
                    /*orderMessages.addAll(((ActivityMessages) childContext).getActivityMessages());*/
                }
            }

            /*context.getSeedData().getOrder().setOrderMessages(orderMessages);*/
            return context.getSeedData().getOrder();
        } catch (WorkflowException e) {
            throw new AddToCartException("Could not add to cart", getCartOperationExceptionRootCause(e));
        }
    }

    /**
     * This method will return the exception that is immediately below the deepest
     * WorkflowException in the current stack trace.
     *
     * @param e the workflow exception that contains the requested root cause
     * @return the root cause of the workflow exception
     */
    private Throwable getCartOperationExceptionRootCause(WorkflowException e) {
        Throwable cause = e.getCause();
        if (cause == null) {
            return e;
        }

        Throwable currentCause = cause;
        while (currentCause.getCause() != null) {
            currentCause = currentCause.getCause();
            if (currentCause instanceof WorkflowException) {
                cause = currentCause.getCause();
            }
        }

        return cause;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = {UpdateCartException.class, RemoveFromCartException.class},
            isolation = Isolation.READ_COMMITTED)
    public Order updateItemQuantity(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
            throws UpdateCartException, RemoveFromCartException {
        preValidateCartOperation(findOrderById(orderId, FetchMode.FETCHED));
        preValidateUpdateQuantityOperation(findOrderById(orderId, FetchMode.FETCHED), orderItemRequestDTO);
        if (orderItemRequestDTO.getQuantity() == 0) {
            return removeItem(orderId, orderItemRequestDTO.getOrderItemId(), priceOrder);
        }

        try {
            CartOperationRequest cartOpRequest = new CartOperationRequest(findOrderById(orderId, FetchMode.FETCHED), orderItemRequestDTO, priceOrder);
            ProcessContext<CartOperationRequest> context = (ProcessContext<CartOperationRequest>) updateItemWorkflow.doActivities(cartOpRequest);
            /*context.getSeedData().getOrder().getOrderMessages().addAll(((ActivityMessages) context).getActivityMessages());*/
            return context.getSeedData().getOrder();
        } catch (WorkflowException e) {
            throw new UpdateCartException("Could not update cart quantity", getCartOperationExceptionRootCause(e));
        }
    }

    @SuppressWarnings("unchecked")
    private Order removeItemInternal(long orderId, long orderItemId, boolean priceOrder) throws WorkflowException {
        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        orderItemRequestDTO.setOrderItemId(orderItemId);

        Order order = findOrderById(orderId, FetchMode.FETCHED);

        CartOperationRequest cartOpRequest = new CartOperationRequest(order, orderItemRequestDTO, priceOrder);
        ProcessContext<CartOperationRequest> context = (ProcessContext<CartOperationRequest>) removeItemWorkflow.doActivities(cartOpRequest);

        // It means we have a wishlist and need to remove orderItem without any rollbacks
        if(order.getOrderStatus() == OrderStatus.NAMED) {
            return order;
        }

        rollbackOrderItemForOrder(context.getSeedData().getOrderItem());
        /*context.getSeedData().getOrder().getOrderMessages().addAll(((ActivityMessages) context).getActivityMessages());*/
        return context.getSeedData().getOrder();
    }

    @Override
    @Transactional(rollbackFor = {RemoveFromCartException.class}, isolation = Isolation.READ_COMMITTED)
    public Order removeItem(long orderId, long orderItemId, boolean priceOrder) throws RemoveFromCartException {
        /*preValidateCartOperation(findOrderById(orderId, FetchMode.LAZY));*/
        try {
            OrderItem oi = orderItemService.findOrderItemById(orderItemId, FetchMode.LAZY);
            if (oi == null) {
                throw new WorkflowException(new ItemNotFoundException());
            }
            return removeItemInternal(orderId, orderItemId, priceOrder);
        } catch (WorkflowException e) {
            throw new RemoveFromCartException("Could not remove from cart", getCartOperationExceptionRootCause(e));
        }
    }

    @Override
    public Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, boolean priceOrder)
            throws RemoveFromCartException, AddToCartException {
        Order cartOrder = orderDao.findCartForCustomer(namedOrder.getCustomer().getId(), FetchMode.FETCHED);
        if (cartOrder == null) {
            cartOrder = createNewCartForCustomer(namedOrder.getCustomer(), namedOrder.getCurrency());
        }

        if (moveNamedOrderItems) {
            removeItem(namedOrder.getId(), orderItem.getId(), false);
        }

        OrderItemRequestDTO orderItemRequest = orderItemService.buildOrderItemRequestDTOFromOrderItem(orderItem);
        cartOrder = addItem(cartOrder.getId(), orderItemRequest, priceOrder);

        if (namedOrder.getOrderItems().size() == 0 && deleteEmptyNamedOrders) {
            cancelOrder(namedOrder);
        }

        return cartOrder;
    }

    @Override
    @Transactional(rollbackFor = AddToCartException.class, isolation = Isolation.READ_COMMITTED)
    public Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder) throws AddToCartException, RemoveFromCartException {
        Order cartOrder = orderDao.findCartForCustomer(namedOrder.getCustomer().getId(), FetchMode.FETCHED);
        if (cartOrder == null) {
            cartOrder = createNewCartForCustomer(namedOrder.getCustomer(), namedOrder.getCurrency());
        }
        List<OrderItem> items = new ArrayList<>(namedOrder.getOrderItems());
        for (OrderItem item : items) {
            if (moveNamedOrderItems) {
                removeItem(namedOrder.getId(), item.getId(), false);
            }

            OrderItemRequestDTO orderItemRequest = orderItemService.buildOrderItemRequestDTOFromOrderItem(item);
            cartOrder = addItem(cartOrder.getId(), orderItemRequest, priceOrder);
        }

        if (deleteEmptyNamedOrders) {
            cancelOrder(namedOrder);
        }

        return cartOrder;
    }

    @Override
    @Transactional
    public void removePaymentFromOrder(Order order, OrderPayment paymentInfo) {
        checkValueIsNotNull(order, ORDER_OBJECT);

        order = findOrderById(order.getId(), FetchMode.LAZY);
        OrderPayment orderPayment = order.getOrderPayment();
        if(orderPayment != null && orderPayment.equals(paymentInfo)) {
            order.setOrderPayment(null);
        }
    }

    @Override
    @Transactional
    public void deleteOrder(Order cart) {
        checkValueIsNotNull(cart, CART_OBJECT);
        if(cart.getCustomer() != null){
            rollbackAllOrderItemsForOrder(cart);
        }
        orderDao.delete(cart);
    }

    @Override
    @Transactional/*(isolation = Isolation.READ_COMMITTED)*/
    public void deleteOrder(long id) {
        if(id < 1){
            throw new IllegalArgumentException("Bad order ID is given: "+ id);
        }
        Order order = orderDao.findOrderById(id, FetchMode.FETCHED);
        deleteOrder(order);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order removeInactiveItems(long orderId, boolean priceOrder) {
        Order order = findOrderById(orderId, FetchMode.FETCHED);
        checkValueIsNotNull(order, ORDER_OBJECT);
        Iterator<OrderItem> iterator = order.getOrderItems().iterator();

        while (iterator.hasNext()){
            OrderItem orderItem = iterator.next();
            Product product = catalogService.findProductById(orderItem.getId(), FetchMode.LAZY);
            if(!product.isActive()){
                iterator.remove();
            }
        }
        return order;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {UpdateCartException.class})
    public Order updateProductOptionsForItem(long orderId, OrderItemRequestDTO orderItemRequestDTO,
                                             boolean priceOrder) throws UpdateCartException {
        /*try {
            CartOperationRequest cartOpRequest = new CartOperationRequest(findOrderById(orderId, FetchMode.FETCHED),
                    orderItemRequestDTO, priceOrder);
            ProcessContext<CartOperationRequest> context = (ProcessContext<CartOperationRequest>) updateProductOptionsForItemWorkflow.doActivities(cartOpRequest);
            context.getSeedData().getOrder().getOrderMessages().addAll(((ActivityMessages) context).getActivityMessages());
            return context.getSeedData().getOrder();
        } catch (WorkflowException e) {
            throw new UpdateCartException("Could not product options", getCartOperationExceptionRootCause(e));
        }*/
        return null;
    }

    @Override
    public void preValidateCartOperation(Order cart) {
        ExtensionResultHolder erh = new ExtensionResultHolder();
        extensionManager.getProxy().preValidateCartOperation(cart, erh);
        if (erh.getThrowable() instanceof IllegalCartOperationException) {
            throw ((IllegalCartOperationException) erh.getThrowable());
        } else if (erh.getThrowable() != null) {
            throw new RuntimeException(erh.getThrowable());
        }
    }

    @Override
    public void preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto) {
        ExtensionResultHolder erh = new ExtensionResultHolder();
        extensionManager.getProxy().preValidateUpdateQuantityOperation(cart, dto, erh);
        if (erh.getThrowable() instanceof IllegalCartOperationException) {
            throw ((IllegalCartOperationException) erh.getThrowable());
        } else if (erh.getThrowable() != null) {
            throw new RuntimeException(erh.getThrowable());
        }
    }

    @Override
    @Transactional
    public boolean acquireLock(Order order) {
        return orderDao.acquireLock(order);
    }

    @Override
    @Transactional
    public boolean releaseLock(Order order) {
        return orderDao.releaseLock(order);
    }

    @Override
    public SingleTimeTimerTask createOrderSingleTimeTimerTask(long orderId) {
        if(orderId < 1) {
            throw new IllegalArgumentException("Wrong Order ID is given: "+ orderId);
        }
        return orderDao.createOrderSingleTimeTimerTask(orderId);
    }
}