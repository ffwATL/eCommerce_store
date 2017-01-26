package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer_groups")
public class OfferGroupImpl implements OfferGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = ProductDefault.class)
    @JoinColumn(name = "item_id")
    private List<Product> items;

    @Column(name = "discount")
    private int discount;

    @Column(name = "active")
    private boolean active;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Product> getItems() {
        return items;
    }

    @Override
    public int getDiscount() {
        return discount;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setItems(List<Product> items) {
        this.items = items;
    }

    @Override
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "OfferGroupImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", discount=" + discount +
                ", active=" + active +
                '}';
    }
}