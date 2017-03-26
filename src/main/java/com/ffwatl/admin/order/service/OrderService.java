package com.ffwatl.admin.order.service;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.order.dao.OrderDao;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.order.service.exception.AddToCartException;
import com.ffwatl.admin.order.service.exception.RemoveFromCartException;
import com.ffwatl.admin.order.service.exception.UpdateCartException;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.payment.domain.secure.Referenced;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {


    /**
     * Creates a new Order for the given customer. Generally, you will want to use the customer
     * that is on the current request, which can be grabbed by utilizing the CustomerState
     * utility class.
     *
     * The default implementation of this method will provision a new Order in the
     * database and set the current customer as the owner of the order. If the customer has an
     * email address associated with their profile, that will be copied as well. If the customer
     * is a new, anonymous customer, his username will be set to his database id.
     *
     * @param customer
     * @return the newly created order
     */
    Order createNewCartForCustomer(User customer, Currency currency);

    /**
     * Creates a new Order for the given customer with the given name. Typically, this represents
     * a "wishlist" order that the customer can save but not check out with.
     *
     * @param name the wishlist name
     * @param customer
     * @return the newly created named order
     */
    Order createNamedOrderForCustomer(String name, User customer);

    /**
     * Looks up an Order by the given customer and a specified order name.
     *
     * This is typically used to retrieve a "wishlist" order.
     *
     * @see #createNamedOrderForCustomer(String name, User customer)
     *
     * @param name
     * @param customer
     * @return the named order requested
     */
    Order findNamedOrderForCustomer(String name, User customer, FetchMode fetchMode);

    /**
     * Looks up an Order by its database id
     *
     * @param orderId
     * @return the requested Order
     */
    Order findOrderById(long orderId, FetchMode fetchMode);

    /**
     * Looks up an Order by its database id
     * and optionally calls refresh to ensure that the entity manager pulls the instance from the DB and not from cache
     *
     * @param orderId
     * @return the requested Order
     */
    Order findOrderById(long orderId, boolean refresh);

    /**
     * Looks up the current shopping cart for the customer. Note that a shopping cart is
     * simply an Order with OrderStatus = IN_PROCESS. If for some reason the given customer
     * has more than one current IN_PROCESS Order, the default Broadleaf implementation will
     * return the first match found. Furthermore, also note that the current shopping cart
     * for a customer must never be named -- an Order with a non-null "name" property indicates
     * that it is a wishlist and not a shopping cart.
     *
     * @param customer
     * @return the current shopping cart for the customer
     */
    Order findCartForCustomer(User customer, FetchMode fetchMode);

    /**
     * Looks up all Orders for the specified customer, regardless of current OrderStatus
     *
     * @param customer
     * @return the requested Orders
     */
    List<Order> findOrdersForCustomer(User customer, FetchMode fetchMode);

    /**
     * Looks up all Orders for the specified customer that are in the specified OrderStatus.
     *
     * @param customer
     * @param status
     * @return the requested Orders
     */
    List<Order> findOrdersForCustomer(User customer, OrderStatus status, FetchMode fetchMode);

    /**
     * Looks up Orders and returns the order matching the given orderNumber
     *
     * @param orderNumber
     * @return the requested Order
     */
    Order findOrderByOrderNumber(String orderNumber, FetchMode fetchMode);

    /**
     * Returns all OrderPayment objects that are associated with the given order
     *
     * @param order
     * @return the list of all OrderPayment objects
     */
    List<OrderPayment> findPaymentsForOrder(Order order, FetchMode fetchMode);

    /**
     * Associates a given OrderPayment with an Order and then saves the order. Note that it is acceptable for the
     * securePaymentInfo to be null. For example, if the secure credit card details are
     * handled by a third party, a given application may never have associated securePaymentInfos
     *
     * @param order
     * @param payment
     * @param securePaymentInfo - null if it doesn't exist
     * @return the persisted version of the OrderPayment
     */
    OrderPayment addPaymentToOrder(Order order, OrderPayment payment, Referenced securePaymentInfo);

    /**
     * Persists the given order to the database. If the priceOrder flag is set to true,
     * the pricing workflow will execute before the order is written to the database.
     * Generally, you will want to price the order in every request scope once, and
     * preferably on the last call to save() for performance reasons.
     *
     * However, if you have logic that depends on the Order being priced, there are no
     * issues with saving as many times as necessary.
     *
     * @param order
     * @param priceOrder
     * @return the persisted Order, which will be a different instance than the Order passed in
     */
    @Transactional
    Order save(Order order, boolean priceOrder) throws PricingException;

    /**
     * Saves the given <b>order</b> while optionally repricing the order (meaning, going through the pricing workflow)
     * along with updating the prices of individual items in the order, as opposed to just pricing taxes/shipping/etc.
     *
     * @param order
     * @param priceOrder
     * @param repriceItems whether or not to reprice the items inside of the order via {@link Order#updatePrices()}
     * @return the persisted Order, which will be a different instance than the Order passed in
     * @throws PricingException
     */
    Order save(Order order, boolean priceOrder, boolean repriceItems) throws PricingException /*throws PricingException*/;

    /**
     * Deletes the given order. Note that the default Broadleaf implementation in
     * OrderServiceImpl will actually remove the Order instance from the database.
     *
     * @param order
     */
    void cancelOrder(Order order);

    /**
     * Adds the given OfferCode to the order. Optionally prices the order as well.
     *
     * @param order
     * @param offerCode
     * @param priceOrder
     * @return the modified Order
     * @throws PricingException
     * @throws OfferMaxUseExceededException
     * @throws OfferException
     */
    Order addOfferCode(Order order, OfferCode offerCode, boolean priceOrder) /*throws PricingException, OfferException*/;

    /**
     * Remove the given OfferCode for the order. Optionally prices the order as well.
     *
     * @param order
     * @param offerCode
     * @param priceOrder
     * @return the modified Order
     * @throws PricingException
     */
    Order removeOfferCode(Order order, OfferCode offerCode, boolean priceOrder) /*throws PricingException*/;

    /**
     * Removes all offer codes for the given order. Optionally prices the order as well.
     *
     * @param order
     * @param priceOrder
     * @return the modified Order
     * @throws PricingException
     */
    Order removeAllOfferCodes(Order order, boolean priceOrder)/* throws PricingException*/;

    /**
     * The null order is the default order for all customers when they initially
     * enter the site. Upon the first addition of a product to a cart, a non-null order
     * will be provisioned for the user.
     *
     * @see org.broadleafcommerce.core.order.domain.NullOrderImpl for more information
     *
     * @return a shared, static, unmodifiable NullOrder
     */
    Order getNullOrder();

    /**
     * Changes the OrderStatus to SUBMITTED
     *
     * @param order to confirm
     * @return the order that was confirmed
     */
    Order confirmOrder(Order order);

    /**
     * Initiates the addItem workflow that will attempt to add the given quantity of the specified item
     * to the Order. The item to be added can be determined in a few different ways. For example, the
     * SKU can be specified directly or it can be determine based on a Product and potentially some
     * specified ProductOptions for that given product.
     *
     * The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively,
     * attributeId and quantity
     *
     * When priceOrder is false, the system will not reprice the order.   This is more performant in
     * cases such as bulk adds where the repricing could be done for the last item only.
     *
     * This method differs from the {@link #addItemWithPriceOverrides(Long, OrderItemRequestDTO, boolean)} in that it
     * will clear any values set on the {@link OrderItemRequestDTO} for the overrideSalePrice or overrideRetailPrice.
     *
     * This design is intended to ensure that override pricing is not called by mistake.   Implementors should
     * use this method when no manual price overrides are allowed.
     *
     * @see OrderItemRequestDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws WorkflowException
     * @throws Throwable
     */
    Order addItem(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException /*throws AddToCartException*/;

    /**
     * Initiates the addItem workflow that will attempt to add the given quantity of the specified item
     * to the Order. The item to be added can be determined in a few different ways. For example, the
     * SKU can be specified directly or it can be determine based on a Product and potentially some
     * specified ProductOptions for that given product.
     *
     * The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively, attributeId and quantity
     *
     * When priceOrder is false, the system will not reprice the order.   This is more performant in
     * cases such as bulk adds where the repricing could be done for the last item only.
     *
     * As opposed to the {@link #addItem(Long, OrderItemRequestDTO, boolean)} method, this method allows
     * the passed in {@link OrderItemRequestDTO} to contain values for the overrideSale or overrideRetail
     * price fields.
     *
     * This design is intended to ensure that override pricing is not called by mistake.   Implementors should
     * use this method when manual price overrides are allowed.
     *
     * @see OrderItemRequestDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws WorkflowException
     * @throws Throwable
     */
    Order addItemWithPriceOverrides(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException /*throws AddToCartException*/;

    /**
     * Initiates the updateItem workflow that will attempt to update the item quantity for the specified
     * OrderItem in the given Order. The new quantity is specified in the OrderItemRequestDTO
     *
     * Minimum required parameters for OrderItemRequest: orderItemId, quantity
     *
     * @see OrderItemRequestDTO
     * @param orderId
     * @param orderItemRequest
     * @param priceOrder
     * @return the order the item was added to
     * @throws UpdateCartException
     * @throws RemoveFromCartException
     */
    Order updateItemQuantity(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws UpdateCartException, RemoveFromCartException /*throws UpdateCartException, RemoveFromCartException*/;

    /**
     * Initiates the removeItem workflow that will attempt to remove the specified OrderItem from
     * the given Order
     *
     * @see OrderItemRequestDTO
     * @param orderId
     * @param orderItemId
     * @param priceOrder
     * @return the order the item was added to
     * @throws RemoveFromCartException
     */
    Order removeItem(long orderId, long orderItemId, boolean priceOrder) throws RemoveFromCartException /*throws RemoveFromCartException*/;

    /**
     * Adds the passed in orderItem to the current cart for the same Customer that owns the
     * named order. This method will remove the item from the wishlist.
     *
     * Note that if an item was in a wishlist and is no longer able to be added to the cart,
     * the item will still be removed from the wishlist.
     *
     * Note that this method does not change the association of the OrderItems to the new
     * order -- instead, those OrderItems is completely removed and a new OrderItem that mirrors
     * it is created.
     *
     * @param namedOrder
     * @param orderItem
     * @param priceOrder
     * @return the cart with the requested orderItem added to it
     * @throws RemoveFromCartException
     * @throws AddToCartException
     */
    Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, boolean priceOrder) throws RemoveFromCartException, AddToCartException /*throws RemoveFromCartException, AddToCartException*/;

    /**
     * Adds all orderItems to the current cart from the same Customer that owns the named
     * order.
     *
     * Note that any items that are in the wishlist but are no longer able to be added to a cart
     * will still be removed from the wishlist.
     *
     * Note that this method does not change the association of the OrderItems to the new
     * order -- instead, those OrderItems is completely removed and a new OrderItem that mirrors
     * it is created.
     *
     * @param namedOrder
     * @param priceOrder
     * @return
     * @throws RemoveFromCartException
     * @throws AddToCartException
     */
    Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder) throws AddToCartException, RemoveFromCartException /*throws RemoveFromCartException, AddToCartException*/;

    /**
     * Deletes the OrderPayment Info from the order.
     * Note that this method will also delete any associated Secure OrderPayment Infos if necessary.
     *
     * @param order
     * @param paymentInfo
     */
    void removePaymentFromOrder(Order order, OrderPayment paymentInfo);

    void deleteOrder(Order cart);

    void deleteOrder(long id);

    Order removeInactiveItems(long orderId, boolean priceOrder) /*throws RemoveFromCartException*/;

    /**
     * Since required product option can be added after the item is in the cart, we use this method
     * to apply product option on an existing item in the cart. No validation will happen at this time,
     * as the validation at checkout will take care of any missing product options.
     *
     * @param orderId
     * @param orderItemRequestDTO
     * @param priceOrder
     * @return Order
     * @throws UpdateCartException
     */
    Order updateProductOptionsForItem(long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws UpdateCartException /*throws UpdateCartException*/;


    /**
     * Invokes the extension handler of the same name to provide the ability for a module to throw an exception
     * and interrupt a cart operation.
     *
     * @param cart
     */
    void preValidateCartOperation(Order cart);

    /**
     * Invokes the extension handler of the same name to provide the ability for a module to throw an exception
     * and interrupt an update quantity operation.
     *
     * @param cart
     */
    void preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto);

    /**
     * @see OrderDao#acquireLock(Order)
     * @param order
     * @return whether or not the lock was acquired
     */
    boolean acquireLock(Order order);

    /**
     * @see OrderDao#releaseLock(Order)
     * @param order
     * @return whether or not the lock was released
     */
    boolean releaseLock(Order order);

}