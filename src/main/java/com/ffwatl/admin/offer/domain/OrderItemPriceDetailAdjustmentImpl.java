package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemPriceDetailImpl;

import javax.persistence.*;

@Entity
@Table(name = "order_item_price_detail_adjustment")
public class OrderItemPriceDetailAdjustmentImpl implements OrderItemPriceDetailAdjustment{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderItemPriceDetailImpl.class)
    @JoinColumn(name = "order_item_price_detail_id")
    private OrderItemPriceDetail orderItemPriceDetail;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "adjustment_reason")
    private String reason;

    @Column(name = "adjustment_value")
    private int adjustmentValue;

    @Column(name = "applied_to_sale_price")
    private boolean appliedToSalePrice;

    @Transient
    private int retailPriceValue;
    @Transient
    private int salesPriceValue;

    @Override
    public OrderItemPriceDetailAdjustment init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason) {
        this.orderItemPriceDetail = orderItemPriceDetail;

        setOffer(offer);

        this.reason = reason;
        this.reason = offer.getName();
        return this;
    }

    @Override
    public long getId(){
        return this.id;
    }

    @Override
    public Offer getOffer() {
        return this.offer;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public int getAdjustmentValue() {
        return this.adjustmentValue;
    }

    @Override
    public String getOfferName() {
        return this.offerName;
    }

    @Override
    public OrderItemPriceDetail getOrderItemPriceDetail() {
        return this.orderItemPriceDetail;
    }

    @Override
    public boolean isAppliedToSalePrice() {
        return this.appliedToSalePrice;
    }

    @Override
    public int getRetailPriceValue() {
        return this.retailPriceValue;
    }

    @Override
    public int getSalesPriceValue() {
        return this.salesPriceValue;
    }

    @Override
    public OrderItemPriceDetailAdjustment setOfferName(String offerName) {
        this.offerName = offerName;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail) {
        this.orderItemPriceDetail = orderItemPriceDetail;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setAppliedToSalePrice(boolean appliedToSalePrice) {
        this.appliedToSalePrice = appliedToSalePrice;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setRetailPriceValue(int retailPriceValue) {
        this.retailPriceValue = retailPriceValue;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setSalesPriceValue(int salesPriceValue) {
        this.salesPriceValue = salesPriceValue;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setAdjustmentValue(int adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
        return this;
    }

    @Override
    public OrderItemPriceDetailAdjustment setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemPriceDetailAdjustmentImpl that = (OrderItemPriceDetailAdjustmentImpl) o;

        if (getId() != that.getId()) return false;
        if (adjustmentValue != that.adjustmentValue) return false;
        if (isAppliedToSalePrice() != that.isAppliedToSalePrice()) return false;
        if (getRetailPriceValue() != that.getRetailPriceValue()) return false;
        if (getSalesPriceValue() != that.getSalesPriceValue()) return false;
        if (getOrderItemPriceDetail() != null ? !getOrderItemPriceDetail().equals(that.getOrderItemPriceDetail()) : that.getOrderItemPriceDetail() != null)
            return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        if (getOfferName() != null ? !getOfferName().equals(that.getOfferName()) : that.getOfferName() != null)
            return false;
        return !(getReason() != null ? !getReason().equals(that.getReason()) : that.getReason() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderItemPriceDetail() != null ? getOrderItemPriceDetail().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + (getOfferName() != null ? getOfferName().hashCode() : 0);
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + adjustmentValue;
        result = 31 * result + (isAppliedToSalePrice() ? 1 : 0);
        result = 31 * result + getRetailPriceValue();
        result = 31 * result + getSalesPriceValue();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemPriceDetailAdjustmentImpl{" +
                "id=" + id +
                ", orderItemPriceDetail=" + orderItemPriceDetail +
                ", offer=" + offer +
                ", offerName='" + offerName + '\'' +
                ", reason='" + reason + '\'' +
                ", adjustmentValue=" + adjustmentValue +
                ", appliedToSalePrice=" + appliedToSalePrice +
                ", retailPriceValue=" + retailPriceValue +
                ", salesPriceValue=" + salesPriceValue +
                '}';
    }
}