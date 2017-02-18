package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.OfferServiceExtensionManager;
import com.ffwatl.admin.offer.service.discount.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("item_offer_processor")
public class ItemOfferProcessorImpl extends OrderOfferProcessorImpl implements ItemOfferProcessor{

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void filterItemLevelOffer(PromotableOrder order, List<PromotableCandidateItemOffer> qualifiedItemOffers,
                                     Offer offer) {
        boolean itemLevelQualification = false;

        for (PromotableOrderItem promotableOrderItem : order.getDiscountableOrderItems()) {
            if(couldOfferApplyToOrder(offer, order, promotableOrderItem)) {
                itemLevelQualification = true;
                break;
            }
            for (PromotableFulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
                if(couldOfferApplyToOrder(offer, order, promotableOrderItem, fulfillmentGroup)) {
                    itemLevelQualification = true;
                    break;
                }
            }
        }
        //Item Qualification - new for 1.5!
        if (itemLevelQualification) {
            CandidatePromotionItems candidates = couldOfferApplyToOrderItems(offer,
                    order.getDiscountableOrderItems(offer.isValidOnSale()));
            PromotableCandidateItemOffer candidateOffer = null;

            if (candidates.isMatchedQualifier()) {
                //we don't know the final target yet, so put null for the order item for now
                candidateOffer = createCandidateItemOffer(qualifiedItemOffers, offer, order);
                candidateOffer.getCandidateQualifiersMap().putAll(candidates.getCandidateQualifiersMap());
            }
            if (candidates.isMatchedTarget() && candidates.isMatchedQualifier()) {
                candidateOffer.getCandidateTargetsMap().putAll(candidates.getCandidateTargetsMap());
            }
        }
    }

    /**
     * Create a candidate item offer based on the offer in question and a specific order item
     *
     * @param qualifiedItemOffers the container list for candidate item offers
     * @param offer the offer in question
     * @return the candidate item offer
     */
    protected PromotableCandidateItemOffer createCandidateItemOffer(List<PromotableCandidateItemOffer> qualifiedItemOffers,
                                                                    Offer offer, PromotableOrder promotableOrder) {

        PromotableCandidateItemOffer promotableCandidateItemOffer =
                promotableItemFactory.createPromotableCandidateItemOffer(promotableOrder, offer);
        qualifiedItemOffers.add(promotableCandidateItemOffer);

        return promotableCandidateItemOffer;
    }

    @Override
    public void applyAllItemOffers(List<PromotableCandidateItemOffer> itemOffers, PromotableOrder order) {
        // Iterate through the collection of CandidateItemOffers. Remember that each one is an offer that may apply to a
        // particular OrderItem.  Multiple CandidateItemOffers may contain a reference to the same OrderItem object.
        // The same offer may be applied to different Order Items

        for (PromotableCandidateItemOffer itemOffer : itemOffers) {
            if (offerMeetsSubtotalRequirements(order, itemOffer)) {
                applyItemOffer(order, itemOffer);
            }
        }
    }

    protected void applyItemOffer(PromotableOrder order, PromotableCandidateItemOffer itemOffer) {
        if (applyItemOfferExtension(order, itemOffer)) {
            if (offerServiceUtilities.itemOfferCanBeApplied(itemOffer, order.getAllPromotableOrderItemPriceDetails())) {
                applyItemQualifiersAndTargets(itemOffer, order);
                applyAdjustments(order, itemOffer);
            }
        }
    }

    protected void applyItemQualifiersAndTargets(PromotableCandidateItemOffer itemOffer, PromotableOrder order) {

        splitDetailsIfNecessary(order.getAllPromotableOrderItemPriceDetails());
    }



    /**
     * The itemOffer has been qualified and prior methods added PromotionDiscount objects onto the ItemPriceDetail.
     * This code will convert the PromotionDiscounts into Adjustments
     */
    protected void applyAdjustments(PromotableOrder order, PromotableCandidateItemOffer itemOffer) {
        List<PromotableOrderItemPriceDetail> itemPriceDetails = order.getAllPromotableOrderItemPriceDetails();
        offerServiceUtilities.applyAdjustmentsForItemPriceDetails(itemOffer, itemPriceDetails);
    }

    /**
     * Call out to extension managers.
     * Returns true if the core processing should still be performed for the passed in offer.
     */
    protected boolean applyItemOfferExtension(PromotableOrder order,
                                              PromotableCandidateItemOffer itemOffer) {
        Map<String, Object> contextMap = new HashMap<>();

        if (extensionManager != null) {
            extensionManager.getProxy().applyItemOffer(order, itemOffer, contextMap);
            if (contextMap.get(OfferServiceExtensionManager.STOP_PROCESSING) != null) {
                return false;
            }
        }
        return true;
    }

    protected boolean offerMeetsSubtotalRequirements(PromotableOrder order, PromotableCandidateItemOffer itemOffer) {
        if (itemOffer.getOffer().getQualifyingItemSubTotal() == 0 ||
                itemOffer.getOffer().getQualifyingItemSubTotal() < 0) {
            return true;
        }

        //TODO:  Check subtotal requirement before continuing

        return false;
    }

    @Override
    public void applyAndCompareOrderAndItemOffers(PromotableOrder order,
                                                  List<PromotableCandidateOrderOffer> qualifiedOrderOffers,
                                                  List<PromotableCandidateItemOffer> qualifiedItemOffers) {

    }

    @Override
    public void filterOffers(PromotableOrder order, List<Offer> filteredOffers,
                             List<PromotableCandidateOrderOffer> qualifiedOrderOffers,
                             List<PromotableCandidateItemOffer> qualifiedItemOffers) {

    }
}