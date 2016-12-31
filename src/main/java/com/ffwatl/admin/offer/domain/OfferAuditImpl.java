package com.ffwatl.admin.offer.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "offer_audit")
public class OfferAuditImpl implements OfferAudit {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "offer_id")
    private long offerId;
    @Column(name = "offer_code_id")
    private long offerCodeId;
    @Column(name = "order_id")
    private long orderId;
    @Column(name = "customer_id")
    private long customerId;
    @Column(name = "redeemed_date")
    private Date redeemedDate;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getOfferId() {
        return offerId;
    }

    @Override
    public long getOfferCodeId() {
        return offerCodeId;
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public long getCustomerId() {
        return customerId;
    }

    @Override
    public Date getRedeemedDate() {
        return redeemedDate;
    }

    @Override
    public OfferAudit setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OfferAudit setOfferId(long offerId) {
        this.offerId = offerId;
        return this;
    }

    @Override
    public OfferAudit setOfferCodeId(long offerCodeId) {
        this.offerCodeId = offerCodeId;
        return this;
    }

    @Override
    public OfferAudit setOrderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public OfferAudit setCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    @Override
    public OfferAudit setRedeemedDate(Date redeemedDate) {
        this.redeemedDate = redeemedDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferAuditImpl that = (OfferAuditImpl) o;

        if (getId() != that.getId()) return false;
        if (getOfferId() != that.getOfferId()) return false;
        if (getOfferCodeId() != that.getOfferCodeId()) return false;
        if (getOrderId() != that.getOrderId()) return false;
        if (getCustomerId() != that.getCustomerId()) return false;
        return !(getRedeemedDate() != null ? !getRedeemedDate().equals(that.getRedeemedDate()) : that.getRedeemedDate() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (int) (getOfferId() ^ (getOfferId() >>> 32));
        result = 31 * result + (int) (getOfferCodeId() ^ (getOfferCodeId() >>> 32));
        result = 31 * result + (int) (getOrderId() ^ (getOrderId() >>> 32));
        result = 31 * result + (int) (getCustomerId() ^ (getCustomerId() >>> 32));
        result = 31 * result + (getRedeemedDate() != null ? getRedeemedDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferAuditImpl{" +
                "id=" + id +
                ", offerId=" + offerId +
                ", offerCodeId=" + offerCodeId +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", redeemedDate=" + redeemedDate +
                '}';
    }
}