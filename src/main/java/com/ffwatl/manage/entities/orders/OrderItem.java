package com.ffwatl.manage.entities.orders;


import com.ffwatl.manage.entities.currency.Currency;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.clothes.size.Size;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long itemId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private ItemGroup itemGroup;

    @Embedded
    private I18n name;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Size size;

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

    public long getItemId() {
        return itemId;
    }

    public I18n getName() {
        return name;
    }

    public Size getSize() {
        return size;
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

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setName(I18n name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", name=" + name +
                ", size=" + size +
                ", shortName='" + shortName + '\'' +
                ", qty=" + qty +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", currency=" + currency +
                '}';
    }
}