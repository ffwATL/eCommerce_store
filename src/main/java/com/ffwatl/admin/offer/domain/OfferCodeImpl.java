package com.ffwatl.admin.offer.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "offer_codes")
public class OfferCodeImpl implements OfferCode{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = OfferImpl.class)
    private Offer offer;

    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    private boolean active;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public String getOfferCode() {
        return offerCode;
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
    public boolean isActive() {
        return active;
    }

    @Override
    public OfferCode setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OfferCode setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public OfferCode setOfferCode(String offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    @Override
    public OfferCode setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    @Override
    public OfferCode setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    @Override
    public OfferCode setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferCodeImpl offerCode1 = (OfferCodeImpl) o;

        if (getId() != offerCode1.getId()) return false;
        if (getOfferCode() != null ? !getOfferCode().equals(offerCode1.getOfferCode()) : offerCode1.getOfferCode() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(offerCode1.getStartDate()) : offerCode1.getStartDate() != null)
            return false;
        return !(getEndDate() != null ? !getEndDate().equals(offerCode1.getEndDate()) : offerCode1.getEndDate() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOfferCode() != null ? getOfferCode().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferCodeImpl{" +
                "id=" + id +
                ", offerCode='" + offerCode + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                '}';
    }
}