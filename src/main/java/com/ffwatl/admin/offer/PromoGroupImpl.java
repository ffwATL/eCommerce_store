package com.ffwatl.admin.offer;


import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "promo_groups")
public class PromoGroupImpl implements PromoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = ProductDefault.class)
    private List<Product> items;

    private int discount;

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
        return "PromoGroupImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", discount=" + discount +
                ", active=" + active +
                '}';
    }
}