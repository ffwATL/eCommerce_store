package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateItemOffer;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.ExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultHolder;
import com.ffwatl.common.extension.ExtensionResultStatusType;

import java.util.List;
import java.util.Map;

public interface OfferServiceExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType applyAdditionalFilters(List<Offer> offers);

    /**
     * Allows module extension to add additional offer codes to the list, given the customer
     */
    ExtensionResultStatusType buildOfferCodeListForCustomer(User customer, List<OfferCode> offerCodes);

    /**
     * Modules may extend the calculatePotentialSavings method.   Once the handlers run, the
     * contextMap will be checked for an entry with a key of "savings".    If that entry returns a
     * non-null Money, that value will be returned from the calling method.
     *
     * Otherwise, the map will be checked for an entry with a key of "quantity".   If a non-null Integer is
     * returned, that value will replace the quantity call in the normal call to calculatePotentialSavings.
     *
     * This extension is utilized by one or more BLC enterprise modules including Subscription.
     */
    ExtensionResultStatusType calculatePotentialSavings(PromotableCandidateItemOffer itemOffer,
                                                               PromotableOrderItem item, int quantity, Map<String, Object> contextMap);

    /**
     * Modules may need to clear additional offer details when resetPriceDetails is called.
     */
    ExtensionResultStatusType resetPriceDetails(PromotableOrderItem item);

    /**
     * Modules may need to extend the applyItemOffer logic
     *
     * For example, a subscription module might creates future payment adjustments.
     *
     * The module add an attribute of type Boolean to the contextMap named "stopProcessing" indicating to
     * the core offer engine that further adjustment processing is not needed.
     */
    ExtensionResultStatusType applyItemOffer(PromotableOrder order, PromotableCandidateItemOffer itemOffer,
                                                    Map<String, Object> contextMap);

    /**
     * Allows a module to amend the data that synchronizes the {@link PromotableOrder} with the {@link Order}
     */
    ExtensionResultStatusType synchronizeAdjustmentsAndPrices(PromotableOrder order);

    /**
     * Allows a module to finalize adjustments.
     */
    ExtensionResultStatusType chooseSaleOrRetailAdjustments(PromotableOrder order);

    /**
     * Allows module extensions to add a create a new instance of OrderItemPriceDetailAdjustment.
     * The module should add the value to the resultHolder.getContextMap() with a key of "OrderItemPriceDetailAdjustment"
     */
    ExtensionResultStatusType createOrderItemPriceDetailAdjustment(ExtensionResultHolder<?> resultHolder,
                                                                   OrderItemPriceDetail itemDetail);
}
