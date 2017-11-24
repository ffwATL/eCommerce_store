package com.ffwatl.admin.catalog.domain.dto.response;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author mmed 11/15/17
 */
public interface CatalogItem {
    List<ProductAttribute> getSize();

    long getId();

    Currency getCurrency();

    ProductCategory getItemGroup();

    I18n getItemName();

    int getQuantity();

    Color getColor();

    int getOriginPrice();

    int getSalePrice();

    boolean isActive();

    Date getImportDate();

    Timestamp getLastChangeDate();

    String getThumbnailUrl();
}
