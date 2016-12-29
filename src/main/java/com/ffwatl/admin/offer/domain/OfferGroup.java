package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.catalog.domain.Product;

import java.util.List;


public interface OfferGroup {
    long getId();

    String getName();

    List<Product> getItems();

    int getDiscount();

    boolean isActive();

    void setId(long id);

    void setName(String name);

    void setItems(List<Product> items);

    void setDiscount(int discount);

    void setActive(boolean active);
}
