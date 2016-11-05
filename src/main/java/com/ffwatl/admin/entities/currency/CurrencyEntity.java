package com.ffwatl.admin.entities.currency;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "currency")
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double uah = 1;
    private double usd;
    private double eur;
    private double gbp;

    private Date date;

    public long getId() {
        return id;
    }

    public double getUah() {
        return uah;
    }

    public double getUsd() {
        return usd;
    }

    public double getEur() {
        return eur;
    }

    public double getGbp() {
        return gbp;
    }

    public Date getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUah(double uah) {
        this.uah = uah;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public void setEur(double eur) {
        this.eur = eur;
    }

    public void setGbp(double gbp) {
        this.gbp = gbp;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}