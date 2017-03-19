package com.ffwatl.admin.order.fulfillment;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.FulfillmentOptionImpl;

import javax.persistence.*;

/**
 * @author ffw_ATL.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "fixed_price_fulfillment_option")
public class FixedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements FixedPriceFulfillmentOption {


    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "currency")
    private Currency currency;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "FixedPriceFulfillmentOptionImpl{" +
                "price=" + price +
                ", currency=" + currency +
                '}';
    }
}
