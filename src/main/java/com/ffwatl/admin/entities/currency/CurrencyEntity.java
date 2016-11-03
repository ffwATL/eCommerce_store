package com.ffwatl.admin.entities.currency;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency")
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double UAH = 1;
    private double USD;
    private double EUR;
    private double GBP;

    private Date date;

    public long getId() {
        return id;
    }

    public double getUAH() {
        return UAH;
    }

    public double getUSD() {
        return USD;
    }

    public double getEUR() {
        return EUR;
    }

    public double getGBP() {
        return GBP;
    }

    public Date getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUAH(double UAH) {
        this.UAH = UAH;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public void setEUR(double EUR) {
        this.EUR = EUR;
    }

    public void setGBP(double GBP) {
        this.GBP = GBP;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}