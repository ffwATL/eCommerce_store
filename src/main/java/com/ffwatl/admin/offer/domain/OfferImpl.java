package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.service.OfferDiscountType;
import com.ffwatl.admin.offer.service.OfferType;
import com.ffwatl.common.rule.Rule;
import com.ffwatl.common.rule.RuleImpl;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "offers")
public class OfferImpl implements Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "offer_name")
    private String name;

    @Embedded
    private I18n description;

    @Column(name = "offer_type")
    private String offerType;

    @Column(name = "offer_discount_type")
    private String offerDiscountType;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "value")
    private int value;

    @Column(name = "valid_on_sale")
    private boolean validOnSale;

    @Column(name = "max_uses_by_customer")
    private int maxUsesByCustomer = 1;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
                    /*mappedBy = "id", */targetEntity = RuleImpl.class)
    @MapKey(name="type")
    private Map<String, Rule> matchRules;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH,
                    targetEntity = OfferCodeImpl.class)
    @JoinColumn(name = "offer_codes_id")
    private Set<OfferCode> offerCodes;

    @Column(name = "combinable_with_other_offers")
    private boolean combinableWithOtherOffers;

    @Column(name = "qualifying_item_subtotal")
    private int qualifyingItemSubtotal;

    @Column(name = "totalitarian_offer")
    private boolean totalitarianOffer;

    @Column(name = "automatically_added")
    private boolean automaticallyAdded;

    @Column(name = "currency")
    private Currency currency;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public I18n getDescription() {
        return description;
    }

    @Override
    public OfferType getType() {
        return OfferType.getInstance(offerType);
    }

    @Override
    public OfferDiscountType getDiscountType() {
        return OfferDiscountType.getInstance(offerDiscountType);
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public boolean isValidOnSale() {
        return validOnSale;
    }

    @Override
    public int getMaxUsesPerCustomer() {
        return maxUsesByCustomer;
    }

    @Override
    public Map<String, Rule> getMatchRules() {
        return matchRules;
    }

    @Override
    public Set<OfferCode> getOfferCodes() {
        return offerCodes;
    }

    @Override
    public boolean isCombinableWithOtherOffers() {
        return combinableWithOtherOffers;
    }

    @Override
    public int getQualifyingItemSubTotal() {
        return qualifyingItemSubtotal;
    }

    @Override
    public boolean isTotalitarianOffer() {
        return totalitarianOffer;
    }

    @Override
    public boolean isAutomaticallyAdded() {
        return automaticallyAdded;
    }

    @Override
    public Currency getOfferCurrency() {
        return currency;
    }

    @Override
    public Offer setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Offer setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Offer setDescription(I18n description) {
        this.description = description;
        return this;
    }

    @Override
    public Offer setOfferType(OfferType offerType) {
        this.offerType = offerType.getType();
        return this;
    }

    @Override
    public Offer setOfferType(String offerType) {
        this.offerType = offerType;
        return this;
    }

    @Override
    public Offer setDiscountType(OfferDiscountType offerDiscountType) {
        this.offerDiscountType = offerDiscountType.getType();
        return this;
    }

    @Override
    public Offer setDiscountType(String offerDiscountType) {
        this.offerDiscountType = offerDiscountType;
        return this;
    }

    @Override
    public Offer setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public Offer setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public Offer setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public Offer setValidOnSale(boolean validOnSale) {
        this.validOnSale = validOnSale;
        return this;
    }

    @Override
    public Offer setMaxUsesPerCustomer(int maxUses) {
        this.maxUsesByCustomer = maxUses;
        return this;
    }

    @Override
    public Offer setMatchRules(Map<String, Rule> matchRules) {
        this.matchRules = matchRules;
        return this;
    }

    @Override
    public Offer setOfferCodes(Set<OfferCode> offerCodes) {
        this.offerCodes = offerCodes;
        return this;
    }

    @Override
    public Offer setCombinableWithOtherOffers(boolean combinableWithOtherOffers) {
        this.combinableWithOtherOffers = combinableWithOtherOffers;
        return this;
    }

    @Override
    public Offer setQualifyingItemSubTotal(int qualifyingItemSubtotal) {
        this.qualifyingItemSubtotal = qualifyingItemSubtotal;
        return this;
    }

    @Override
    public Offer setTotalitarianOffer(boolean totalitarianOffer) {
        this.totalitarianOffer = totalitarianOffer;
        return this;
    }

    @Override
    public Offer setAutomaticallyAdded(boolean automaticallyAdded) {
        this.automaticallyAdded = automaticallyAdded;
        return this;
    }

    @Override
    public Offer setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferImpl offer = (OfferImpl) o;

        if (getId() != offer.getId()) return false;
        if (getValue() != offer.getValue()) return false;
        if (isValidOnSale() != offer.isValidOnSale()) return false;
        if (maxUsesByCustomer != offer.maxUsesByCustomer) return false;
        if (isCombinableWithOtherOffers() != offer.isCombinableWithOtherOffers()) return false;
        if (qualifyingItemSubtotal != offer.qualifyingItemSubtotal) return false;
        if (isTotalitarianOffer() != offer.isTotalitarianOffer()) return false;
        if (isAutomaticallyAdded() != offer.isAutomaticallyAdded()) return false;
        if (getName() != null ? !getName().equals(offer.getName()) : offer.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(offer.getDescription()) : offer.getDescription() != null)
            return false;
        if (offerType != null ? !offerType.equals(offer.offerType) : offer.offerType != null) return false;
        if (offerDiscountType != null ? !offerDiscountType.equals(offer.offerDiscountType) : offer.offerDiscountType != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(offer.getStartDate()) : offer.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(offer.getEndDate()) : offer.getEndDate() != null) return false;
        if (getMatchRules() != null ? !getMatchRules().equals(offer.getMatchRules()) : offer.getMatchRules() != null)
            return false;
        return !(getOfferCodes() != null ? !getOfferCodes().equals(offer.getOfferCodes()) : offer.getOfferCodes() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (offerType != null ? offerType.hashCode() : 0);
        result = 31 * result + (offerDiscountType != null ? offerDiscountType.hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + getValue();
        result = 31 * result + (isValidOnSale() ? 1 : 0);
        result = 31 * result + maxUsesByCustomer;
        result = 31 * result + (getMatchRules() != null ? getMatchRules().hashCode() : 0);
        result = 31 * result + (getOfferCodes() != null ? getOfferCodes().hashCode() : 0);
        result = 31 * result + (isCombinableWithOtherOffers() ? 1 : 0);
        result = 31 * result + qualifyingItemSubtotal;
        result = 31 * result + (isTotalitarianOffer() ? 1 : 0);
        result = 31 * result + (isAutomaticallyAdded() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                ", offerType='" + offerType + '\'' +
                ", offerDiscountType='" + offerDiscountType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", value=" + value +
                ", validOnSale=" + validOnSale +
                ", maxUsesByCustomer=" + maxUsesByCustomer +
                ", matchRules=" + matchRules +
                ", offerCodes=" + offerCodes +
                ", combinableWithOtherOffers=" + combinableWithOtherOffers +
                ", qualifyingItemSubtotal=" + qualifyingItemSubtotal +
                ", totalitarianOffer=" + totalitarianOffer +
                ", automaticallyAdded=" + automaticallyAdded +
                '}';
    }
}