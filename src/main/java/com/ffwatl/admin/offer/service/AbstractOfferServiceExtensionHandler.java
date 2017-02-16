package com.ffwatl.admin.offer.service;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateItemOffer;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.AbstractExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultHolder;
import com.ffwatl.common.extension.ExtensionResultStatusType;

import java.util.List;
import java.util.Map;


public abstract class AbstractOfferServiceExtensionHandler extends AbstractExtensionHandler implements OfferServiceExtensionHandler{

    @Override
    public ExtensionResultStatusType applyAdditionalFilters(List<Offer> offers) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType buildOfferCodeListForCustomer(User customer, List<OfferCode> offerCodes) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType calculatePotentialSavings(PromotableCandidateItemOffer itemOffer,
                                                               PromotableOrderItem item, int quantity, Map<String, Object> contextMap) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType resetPriceDetails(PromotableOrderItem item) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType applyItemOffer(PromotableOrder order, PromotableCandidateItemOffer itemOffer,
                                                    Map<String, Object> contextMap) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType synchronizeAdjustmentsAndPrices(PromotableOrder order) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType chooseSaleOrRetailAdjustments(PromotableOrder order) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType createOrderItemPriceDetailAdjustment(ExtensionResultHolder<?> resultHolder,
                                                                          OrderItemPriceDetail itemDetail) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}