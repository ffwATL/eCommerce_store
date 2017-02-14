package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.CandidateOrderOffer;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.domain.OrderAdjustment;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.user.domain.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Defines an order in store. There are several key items to be aware of with the Order.
 *
 * 1.  Carts are also Orders that are in a Pending status
 *
 * 2.  Wishlists (and similar) are "NamedOrders"
 *
 * 3.  Orders have several price related methods that are useful when displaying totals on the cart.
 * 3a.    getSubTotal() :  The total of all order items and their adjustments exclusive of taxes
 * 3b.    getOrderAdjustmentsValue() :  The total of all order adjustments
 * 3c.    getTotal() : The order total (equivalent of getSubTotal() - getOrderAdjustmentsValue())
 *
 * 4.  Order payments are represented with OrderPayment objects.
 *
 * 5.  Order shipping (e.g. fulfillment) are represented with Fulfillment objects.
 */
public interface Order extends Serializable{

    long getId();

    String getOrderNumber();

    /**
     * Gets the name of the order, mainly in order to support wishlists.
     * @return the name of the order
     */
    String getName();

    /**
     * Returns the subtotal price for the order.  The subtotal price is the price of all order items
     * with item offers applied.  The subtotal does not take into account the order promotions or any
     * shipping costs that apply to this order.
     * @return the total item price with offers applied
     */
    int getSubTotal();

    /**
     * The grand total of this {@link Order} which includes all shipping costs and taxes,
     * as well as any adjustments from promotions.
     * @return the grand total price of this {@link Order}
     */
    int getTotal();

    /**
     * Gets the {@link User} for this {@link Order}.
     * @return the {@link User} for this {@link Order}.
     */
    User getCustomer();

    /**
     * Gets the status of the Order.
     * @return the status of the Order.
     */
    OrderStatus getOrderStatus();

    /**
     * Gets all the {@link OrderItem}s included in this {@link Order}.
     * @return all the {@link OrderItem}s included in this {@link Order}.
     */
    List<OrderItem> getOrderItems();

    /**
     * Gets the {@link FulfillmentGroup} associated with this {@link Order}.
     * @return the {@link FulfillmentGroup}s associated with this {@link Order}
     */
    FulfillmentGroup getFulfillmentGroup();

    /**
     * Gets the {@link Offer}s that could potentially apply to this {@link Order}. Used in the promotion engine.
     * @return the {@link Offer}s that could potentially apply to this {@link Order}.
     */
    Set<CandidateOrderOffer> getCandidateOrderOffers();

    /**
     * Returns a unmodifiable List of OrderAdjustment.  To modify the List of OrderAdjustment, please
     * use the addOrderAdjustments or removeAllOrderAdjustments methods.
     * @return a unmodifiable List of OrderItemAdjustment
     */
    Set<OrderAdjustment> getOrderAdjustments();

    /**
     * Gets the date that this {@link Order} was submitted.  Note that if this date is non-null,
     * then the following should also be true:
     *  <ul>
     *      <li>{@link #getOrderStatus()} should return {@link OrderStatus#SUBMITTED}</li>
     *      <li>{@link #getOrderNumber()} should return a non-null value</li>
     *  </ul>
     * @return the date that this {@link Order} was submitted.
     */
    Date getSubmitDate();

    /**
     * Gets the total fulfillment costs that should be charged for this {@link Order}. This value should be equivalent
     * to the summation of {@link FulfillmentGroup#getTotal()} for each {@link FulfillmentGroup} associated with this
     * {@link Order}
     * @return the total fulfillment cost of this {@link Order}
     */
    int getTotalFulfillmentCharges();

    /**
     * Gets the {@link OrderPayment} associated with this {@link Order}. An {@link Order} can have only one
     * {@link OrderPayment} associated with it.
     * @return the {@link OrderPayment}s associated with this {@link Order}.
     */
    OrderPayment getOrderPayment();

    OfferCode getOfferCode();

    /**
     * The currency that the {@link Order} is priced in. Note that this is only on {@link Order}
     * since all of the other entities that are related (like {@link FulfillmentGroup} and {@link OrderItem}
     * have a link back to here. This also has the side effect that an {@link Order} can only be priced
     * in a single currency.
     * @return The currency that the {@link Order} is priced in.
     */
    Currency getCurrency();

    String getFulfillmentStatus();

    /**
     * Returns the discount value of all the applied item offers for this order.  This value is already
     * deducted from the order subTotal.
     * @return the discount value of all the applied item offers for this order
     */
    int getItemAdjustmentsValue();

    int getFulfillmentGroupAdjustmentsValue();

    /**
     * Returns the discount value of all the applied order offers.  The value returned from this
     * method should be subtracted from the getSubTotal() to get the order price with all item and
     * order offers applied.
     * @return the discount value of all applied order offers.
     */
    int getOrderAdjustmentsValue();

    /**
     * Returns the total discount value for all applied item and order offers in the order.  The return
     * value should not be used with getSubTotal() to calculate the final price, since getSubTotal()
     * already takes into account the applied item offers.
     * @return the total discount of all applied item and order offers
     */
    int getTotalAdjustmentsValue();

    /**
     * This method returns the total number of items in this order. It iterates through all of the
     * discrete order items and sums up the quantity. This method is useful for display to the customer
     * the current number of "physical" items in the cart.
     * @return the number of items in the order.
     */
    int getProductCount();

    /**
     * Returns true if this item has order adjustments.
     * @return true if this item has order adjustments.
     */
    boolean getHasOrderAdjustments();


    Order setId(long id);

    Order setOrderNumber(String orderNumber);

    Order setName(String name);

    Order setSubTotal(int subTotal);

    Order setTotal(int total);

    Order setCustomer(User customer);

    Order setOrderStatus(OrderStatus orderStatus);

    Order setOrderItems(List<OrderItem> orderItems);

    Order setFulfillmentGroups(FulfillmentGroup fulfillmentGroup);

    Order setCandidateOrderOffers(Set<CandidateOrderOffer> candidateOrderOffers);

    Order setOrderAdjustments(Set<OrderAdjustment> orderAdjustments);

    Order setSubmitDate(Date submitDate);

    Order setTotalFulfillmentCharges(int totalFulfillmentCharges);

    Order setOrderPayment(OrderPayment orderPayment);

    Order setOfferCode(OfferCode addedOfferCode);

    Order setCurrency(Currency currency);

    /**
     * Adds an {@link OrderItem} to the list of {@link OrderItem}s already associated with this {@link Order}
     * @param orderItem the {@link OrderItem} to add to this {@link Order}
     */
    Order addOrderItem(OrderItem orderItem);

    void removeLastOrderItem();

    void removeFirstOrderItem();

    void removeOrderItem(OrderItem orderItem);

    void removeOrderItem(int index);

    void removeAllOrderItems();

    /**
     * Returns the sum of the item totals.
     * @return the sum of the item totals.
     */
    int calculateSubTotal();

}