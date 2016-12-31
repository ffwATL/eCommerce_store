package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.service.OfferDiscountType;
import com.ffwatl.admin.offer.service.OfferType;
import com.ffwatl.common.rule.Rule;

import java.util.Date;
import java.util.Map;
import java.util.Set;


public interface Offer {

    long getId();
    String getName();
    I18n getDescription();
    OfferType getType();
    OfferDiscountType getDiscountType();
    Date getStartDate();
    Date getEndDate();
    int getValue(); //Offer value ie discount value
    boolean isValidOnSale();
    int getMaxUsesPerCustomer();
    Map<String, Rule> getMatchRules();

    /**
     * Returns the offer codes that can be used to retrieve this Offer. These codes would be used in
     * situations where this Offer is not automatically considered
     * (meaning {@link Offer#isAutomaticallyAdded()} is false}
     */
    Set<OfferCode> getOfferCodes();

    /**
     * Returns false if this offer is not combinable with other offers of the same type.
     * For example, if this is an Item offer it could be combined with other Order or FG offers
     * but it cannot be combined with other Item offers.
     */
    boolean isCombinableWithOtherOffers();
    int getQualifyingItemSubTotal();
    boolean isTotalitarianOffer();

    /**
     * Returns true if the offer system should automatically add this offer for consideration
     * (versus requiring a code or other delivery mechanism). This does not guarantee that the offer
     * will qualify.   All rules associated with this offer must still pass.
     * A true value here just means that the offer will be considered.
     *
     * @return true if the offer system should automatically add this offer for consideration.
     */
    boolean isAutomaticallyAdded();

    Offer setId(long id);
    Offer setName(String name);
    Offer setDescription(I18n description);
    Offer setOfferType(OfferType offerType);

    Offer setOfferType(String offerType);

    Offer setDiscountType(OfferDiscountType offerDiscountType);

    Offer setDiscountType(String offerDiscountType);

    Offer setStartDate(Date startDate);
    Offer setEndDate(Date endDate);
    Offer setValue(int value);
    Offer setValidOnSale(boolean validOnSale);
    Offer setMaxUsesPerCustomer(int maxUses);
    Offer setMatchRules(Map<String, Rule> matchRules);

    /**
     * Sets the offer codes that can be used to retrieve this Offer. These codes would be used in situations
     * where this Offer is not automatically considered (meaning {@link Offer#isAutomaticallyAdded()} is false}
     */
    Offer setOfferCodes(Set<OfferCode> offerCodes);
    Offer setCombinableWithOtherOffers(boolean combinableWithOtherOffers);
    Offer setQualifyingItemSubTotal(int qualifyingItemSubtotal);
    Offer setTotalitarianOffer(boolean totalitarianOffer);
    Offer setAutomaticallyAdded(boolean automaticallyAdded);

}