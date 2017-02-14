package com.ffwatl.admin.currency.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency")
public class CurrencyRateImpl implements CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "value")
    private Double value = 1.0d;

    @Column(name = "currency")
    private Currency currency;

    private Date date;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public CurrencyRate setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public CurrencyRate setValue(Double value) {
        this.value = value;
        return this;
    }

    @Override
    public CurrencyRate setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public CurrencyRate setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRateImpl that = (CurrencyRateImpl) o;

        if (getId() != that.getId()) return false;
        if (Double.compare(that.getValue(), getValue()) != 0) return false;
        if (getCurrency() != that.getCurrency()) return false;
        return !(getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyRateImpl{" +
                "id=" + id +
                ", value=" + value +
                ", currency=" + currency +
                ", date=" + date +
                '}';
    }
}