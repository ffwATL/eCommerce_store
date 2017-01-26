package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.CandidateFulfillmentGroupOffer;
import com.ffwatl.admin.offer.domain.CandidateFulfillmentGroupOfferImpl;
import com.ffwatl.admin.offer.domain.FulfillmentGroupAdjustment;
import com.ffwatl.admin.offer.domain.FulfillmentGroupAdjustmentImpl;
import com.ffwatl.admin.order.service.FulfillmentGroupStatusType;
import com.ffwatl.admin.order.service.FulfillmentType;
import com.ffwatl.admin.user.domain.Address;
import com.ffwatl.admin.user.domain.AddressImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "fulfillment_groups")
public class FulfillmentGroupImpl implements FulfillmentGroup{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = OrderImpl.class, optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = AddressImpl.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(targetEntity = FulfillmentOptionImpl.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fulfillment_option_id")
    private FulfillmentOption fulfillmentOption;

    @Column(name = "retail_fulfillment_price")
    private int retailFulfillmentPrice;

    @Column(name = "fulfillment_price")
    private int fulfillmentPrice;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "fulfillmentGroup",
               fetch = FetchType.LAZY,
               targetEntity = CandidateFulfillmentGroupOfferImpl.class,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JoinColumn(name = "c fulfillment group_offer_id")
    private List<CandidateFulfillmentGroupOffer> candidateFulfillmentGroupOffers = new ArrayList<>();

    @OneToMany(mappedBy = "fulfillmentGroup",
               fetch = FetchType.LAZY,
               targetEntity = FulfillmentGroupAdjustmentImpl.class,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JoinColumn(name = "fulfillment_group_adj_id")
    private Set<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments;

    @Column(name = "status")
    private String status;

    @Embedded
    private I18n deliveryInstructions;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public FulfillmentOption getFulfillmentOption() {
        return fulfillmentOption;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public int getRetailFulfillmentPrice() {
        return retailFulfillmentPrice;
    }

    @Override
    public int getFulfillmentPrice() {
        return fulfillmentPrice;
    }

    @Override
    public FulfillmentType getType() {
        return FulfillmentType.getInstance(type);
    }

    @Override
    public List<CandidateFulfillmentGroupOffer> getCandidateFulfillmentGroupOffers() {
        return candidateFulfillmentGroupOffers;
    }

    @Override
    public Set<FulfillmentGroupAdjustment> getFulfillmentGroupAdjustments() {
        return fulfillmentGroupAdjustments;
    }

    @Override
    public int getFulfillmentGroupAdjustmentsValue() {
        int adjustmentValue = 0;
        for(FulfillmentGroupAdjustment groupAdjustment: fulfillmentGroupAdjustments){
            adjustmentValue += groupAdjustment.getAdjustmentValue();
        }
        return adjustmentValue;
    }

    @Override
    public FulfillmentGroupStatusType getStatus() {
        return FulfillmentGroupStatusType.getInstance(status);
    }

    @Override
    public I18n getDeliveryInstruction() {
        return deliveryInstructions;
    }

    @Override
    public FulfillmentGroup setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public FulfillmentGroup setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public FulfillmentGroup setAddress(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public FulfillmentGroup setFulfillmentOption(FulfillmentOption fulfillmentOption) {
        this.fulfillmentOption = fulfillmentOption;
        return this;
    }

    @Override
    public FulfillmentGroup setRetailFulfillmentPrice(int retailFulfillmentPrice) {
        this.retailFulfillmentPrice = retailFulfillmentPrice;
        return this;
    }

    @Override
    public FulfillmentGroup setFulfillmentPrice(int fulfillmentPrice) {
        this.fulfillmentPrice = fulfillmentPrice;
        return this;
    }

    @Override
    public FulfillmentGroup setType(FulfillmentType type) {
        this.type = type == null ? null : type.getType();
        return this;
    }

    @Override
    public FulfillmentGroup setCandidateFulfillmentGroupOffers(List<CandidateFulfillmentGroupOffer> candidateFulfillmentGroupOffers) {
        this.candidateFulfillmentGroupOffers = candidateFulfillmentGroupOffers;
        return this;
    }

    @Override
    public FulfillmentGroup setFulfillmentGroupAdjustments(Set<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments) {
        this.fulfillmentGroupAdjustments = fulfillmentGroupAdjustments;
        return this;
    }

    @Override
    public FulfillmentGroup addCandidateFulfillmentGroupOffer(CandidateFulfillmentGroupOffer candidateOffer) {
        this.candidateFulfillmentGroupOffers.add(candidateOffer);
        return this;
    }

    @Override
    public FulfillmentGroup removeAllCandidateOffers() {
        if(candidateFulfillmentGroupOffers != null){
            for(CandidateFulfillmentGroupOffer offer: candidateFulfillmentGroupOffers){
                offer.setFulfillmentGroup(null);
            }
            candidateFulfillmentGroupOffers.clear();
        }
        return this;
    }

    @Override
    public FulfillmentGroup setStatus(FulfillmentGroupStatusType status) {
        this.status = status == null ? null: status.getType();
        return this;
    }

    @Override
    public FulfillmentGroup setDeliveryInstruction(I18n deliveryInstruction) {
        this.deliveryInstructions = deliveryInstruction;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentGroupImpl that = (FulfillmentGroupImpl) o;

        if (getId() != that.getId()) return false;
        if (getRetailFulfillmentPrice() != that.getRetailFulfillmentPrice()) return false;
        if (getFulfillmentPrice() != that.getFulfillmentPrice()) return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getFulfillmentOption() != null ? !getFulfillmentOption().equals(that.getFulfillmentOption()) : that.getFulfillmentOption() != null)
            return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if (getCandidateFulfillmentGroupOffers() != null ? !getCandidateFulfillmentGroupOffers().equals(that.getCandidateFulfillmentGroupOffers()) : that.getCandidateFulfillmentGroupOffers() != null)
            return false;
        if (getFulfillmentGroupAdjustments() != null ? !getFulfillmentGroupAdjustments().equals(that.getFulfillmentGroupAdjustments()) : that.getFulfillmentGroupAdjustments() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) return false;
        return !(deliveryInstructions != null ? !deliveryInstructions.equals(that.deliveryInstructions) : that.deliveryInstructions != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getFulfillmentOption() != null ? getFulfillmentOption().hashCode() : 0);
        result = 31 * result + getRetailFulfillmentPrice();
        result = 31 * result + getFulfillmentPrice();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getCandidateFulfillmentGroupOffers() != null ? getCandidateFulfillmentGroupOffers().hashCode() : 0);
        result = 31 * result + (getFulfillmentGroupAdjustments() != null ? getFulfillmentGroupAdjustments().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (deliveryInstructions != null ? deliveryInstructions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupImpl{" +
                "id=" + id +
                ", order=" + order +
                ", address=" + address +
                ", fulfillmentOption=" + fulfillmentOption +
                ", retailFulfillmentPrice=" + retailFulfillmentPrice +
                ", fulfillmentPrice=" + fulfillmentPrice +
                ", type='" + type + '\'' +
                ", candidateFulfillmentGroupOffers=" + candidateFulfillmentGroupOffers +
                ", fulfillmentGroupAdjustments=" + fulfillmentGroupAdjustments +
                ", status='" + status + '\'' +
                ", deliveryInstructions=" + deliveryInstructions +
                '}';
    }
}