package com.ffwatl.admin.entities.promo;


import com.ffwatl.admin.entities.items.DefaultItem;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "promo_groups")
public class PromoGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<DefaultItem> items;

    private int discount;

    private boolean active;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DefaultItem> getItems() {
        return items;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<DefaultItem> items) {
        this.items = items;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PromoGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", discount=" + discount +
                ", active=" + active +
                '}';
    }
}