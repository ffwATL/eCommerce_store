package com.ffwatl.domain.orders;


import com.ffwatl.domain.currency.Currency;
import com.ffwatl.domain.items.Item;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Item item;

    @Column(nullable = false)
    private String shortName;

    private int qty;

    private int originPrice;

    private int salePrice;

    private int discount;

    private Currency currency;

    public long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public String getShortName() {
        return shortName;
    }

    public int getQty() {
        return qty;
    }

    public int getOriginPrice() {
        return originPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getDiscount() {
        return discount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", item=" + item.getItemName() +
                ", shortName='" + shortName + '\'' +
                ", qty=" + qty +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", currency=" + currency +
                '}';
    }
}