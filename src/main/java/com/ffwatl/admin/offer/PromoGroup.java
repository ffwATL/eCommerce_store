package com.ffwatl.admin.offer;

import com.ffwatl.admin.catalog.domain.Product;

import java.util.List;

/**
 * Created by ffw_ATL on 15-Nov-16.
 */
public interface PromoGroup {
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
