package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.call.MergeCartResponse;
import com.ffwatl.admin.order.service.call.ReconstructCartResponse;
import com.ffwatl.admin.user.domain.User;

/**
 * @author ffw_ATL.
 */
public interface MergeCartService {

    /**
     * Merges the anonymous cart with the customer's current cart, taking into consideration the active
     * status of the SKUs to merge. For example, if the customer had a SKU in their anonymous cart that is no longer
     * active, it will not be merged into the new cart.
     *
     * @param customer the customer whose cart is to be merged
     * @param anonymousCartId the anonymous cart id
     * @param priceOrder whether or not to price the order
     * @return the response containing the cart, any items added to the cart, and any items removed from the cart
     * @throws PricingException
     * @throws RemoveFromCartException
     */
    MergeCartResponse mergeCart(User customer, Order anonymousCart, boolean priceOrder) /*throws PricingException, RemoveFromCartException*/;

    /**
     * Delegates to mergeCart(Customer, Order, boolean) with priceOrder set to true
     *
     * Merges the anonymous cart with the customer's current cart, taking into consideration the active
     * status of the SKUs to merge. For example, if the customer had a SKU in their anonymous cart that is no longer
     * active, it will not be merged into the new cart.
     *
     * @param customer the customer whose cart is to be merged
     * @param anonymousCartId the anonymous cart id
     * @return the response containing the cart, any items added to the cart, and any items removed from the cart
     * @throws PricingException
     * @throws RemoveFromCartException
     */
    MergeCartResponse mergeCart(User customer, Order anonymousCart) /*throws PricingException, RemoveFromCartException*/;

    /**
     * Reconstruct the cart using previous stored state taking into
     * consideration sku activation
     *
     * @param customer the customer whose cart is to be reconstructed
     * @return the response containing the cart and any items removed from the cart
     * @throws RemoveFromCartException
     */
    ReconstructCartResponse reconstructCart(User customer, boolean priceOrder) /*throws PricingException, RemoveFromCartException*/;

    /**
     *
     * Delegates to reconstructCart(Customer, boolean) with priceOrder set to true
     *
     * Reconstruct the cart using previous stored state taking into
     * consideration sku activation
     *
     * @param customer the customer whose cart is to be reconstructed
     * @return the response containing the cart and any items removed from the cart
     * @throws PricingException
     * @throws RemoveFromCartException
     */
    ReconstructCartResponse reconstructCart(User customer) /*throws PricingException, RemoveFromCartException*/;
}
