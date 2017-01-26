package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;

@Entity
@Table(name = "customer_offers")
@NamedQueries({
        @NamedQuery(name = "read_customer_offers_by_customer_id",
                query = "SELECT c FROM CustomerOfferImpl c WHERE c.customer.id=:id")
})
public class CustomerOfferImpl implements CustomerOffer{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = UserImpl.class, optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;



    @Override
    public long getId() {
        return id;
    }

    @Override
    public User getCustomer() {
        return customer;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public CustomerOffer setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public CustomerOffer setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public CustomerOffer setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerOfferImpl that = (CustomerOfferImpl) o;

        if (getId() != that.getId()) return false;
        if (getCustomer() != null ? !getCustomer().equals(that.getCustomer()) : that.getCustomer() != null)
            return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CustomerOfferImpl{" +
                "id=" + id +
                ", customer=" + customer +
                ", offer=" + offer +
                '}';
    }
}