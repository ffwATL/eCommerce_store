package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.CandidateFulfillmentGroupOffer;
import com.ffwatl.admin.offer.domain.FulfillmentGroupAdjustment;
import com.ffwatl.admin.order.service.type.FulfillmentGroupStatusType;
import com.ffwatl.admin.order.service.type.FulfillmentType;
import com.ffwatl.admin.user.domain.Address;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * This is the main entity used to hold fulfillment information about an Order. An Order can have
 * multiple FulfillmentGroups to support shipping items to multiple addresses along with fulfilling
 * items multiple ways (ship some overnight, deliver some with digital download). This constraint means
 * that a FulfillmentGroup is unique based on an Address and FulfillmentOption combination. This
 * also means that in the common case for Orders that are being delivered to a single Address and
 * a single way (shipping everything express; ie a single FulfillmentOption) then there will be
 * only 1 FulfillmentGroup for that Order.
 */
public interface FulfillmentGroup extends Serializable {

    long getId();

    Order getOrder();

    FulfillmentOption getFulfillmentOption();

    Address getAddress();

    Set<FulfillmentGroupItem> getFulfillmentGroupItems();

    /**
     * Returns the retail price for this fulfillmentGroup (ie shipping price).
     * Typically only a retail price would be set on a fulfillment group.
     * @return the retail price for this fulfillmentGroup.
     */
    int getRetailFulfillmentPrice();

    int getSaleFulfillmentPrice();

    /**
     * Gets the price to charge for this fulfillmentGroup.
     * Includes the effects of any adjustments such as those that
     * might have been applied by the promotion engine (e.g. free shipping)
     * @return price to charge for this fulfillmentGroup.
     */
    int getFulfillmentPrice();

    FulfillmentType getType();

    List<CandidateFulfillmentGroupOffer> getCandidateFulfillmentGroupOffers();

    Set<FulfillmentGroupAdjustment> getFulfillmentGroupAdjustments();

    int getFulfillmentGroupAdjustmentsValue();

    FulfillmentGroupStatusType getStatus();

    I18n getDeliveryInstruction();


    FulfillmentGroup setId(long id);

    FulfillmentGroup setOrder(Order order);

    FulfillmentGroup setAddress(Address address);

    FulfillmentGroup setFulfillmentGroupItems(Set<FulfillmentGroupItem> fulfillmentGroupItems);

    FulfillmentGroup addFulfillmentGroupItem(FulfillmentGroupItem fulfillmentGroupItem);

    FulfillmentGroup setFulfillmentOption(FulfillmentOption fulfillmentOption);

    /**
     * Sets the retail price for this fulfillmentGroup.
     * @param retailFulfillmentPrice the retail price for this fulfillmentGroup.
     * @return this object.
     */
    FulfillmentGroup setRetailFulfillmentPrice(int retailFulfillmentPrice);

    FulfillmentGroup setSaleFulfillmentPrice(int saleFulfillmentPrice);

    /**
     * Sets the price to charge for this fulfillmentGroup.  Typically set internally by the pricing and
     * promotion engines.
     * @param fulfillmentPrice price to charge for this fulfillmentGroup;
     * @return this object.
     */
    FulfillmentGroup setFulfillmentPrice(int fulfillmentPrice);

    FulfillmentGroup setType(FulfillmentType type);

    FulfillmentGroup setCandidateFulfillmentGroupOffers(List<CandidateFulfillmentGroupOffer> candidateFulfillmentGroupOffers);

    FulfillmentGroup setFulfillmentGroupAdjustments(Set<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments);

    FulfillmentGroup addCandidateFulfillmentGroupOffer(CandidateFulfillmentGroupOffer candidateOffer);

    FulfillmentGroup removeAllCandidateOffers();

    FulfillmentGroup setStatus(FulfillmentGroupStatusType status);

    FulfillmentGroup setDeliveryInstruction(I18n deliveryInstruction);
}