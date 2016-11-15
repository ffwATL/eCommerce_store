package com.ffwatl.admin.offer;

import com.ffwatl.admin.catalog.domain.Category;

import java.util.Date;
import java.util.List;

/**
 * Created by ffw_ATL on 15-Nov-16.
 */
public interface PromoCode {
    long getId();

    String getCode();

    Date getValidTo();

    boolean isActive();

    int getDiscount();

    boolean isValidOnSale();

    List<Category> getValidOnGroup();

    void setId(long id);

    void setCode(String code);

    void setValidTo(Date validTo);

    void setActive(boolean active);

    void setDiscount(int discount);

    void setValidOnSale(boolean validOnSale);

    void setValidOnGroup(List<Category> validOnGroup);
}
