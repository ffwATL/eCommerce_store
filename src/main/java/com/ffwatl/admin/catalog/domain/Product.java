package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface Product {

    long getId();

    List<ProductAttribute> getProductAttributes();

    Brand getBrand();

    I18n getProductName();

    I18n getDescription();

    ProductCategory getProductCategory();

    User getAddedBy();

    String getExtraNotes();

    String getVendorCode();

    boolean isUsed();

    String getMetaInfo();

    String getMetaKeys();

    Currency getCurrency();

    Color getColor();

    int getQuantity();

    int getOriginPrice();

    int getRetailPrice();

    int getSalePrice();

    boolean isActive();

    Date getImportDate();

    Timestamp getLastChangeDate();

    Gender getGender();


    Product setId(long id);

    Product setProductAttributes(List<ProductAttribute> productAttributes);

    Product setBrand(Brand brand);

    Product setDescription(I18n description);

    Product setCurrency(Currency currency);

    Product setCategory(ProductCategory productCategory);

    Product setProductName(I18n itemName);

    Product setColor(Color color);

    Product setOriginPrice(int originPrice);

    Product setRetailPrice(int originPrice);

    Product setSalePrice(int salePrice);

    Product setActive(boolean isActive);

    Product setImportDate(Date importDate);

    Product setMetaInfo(String metaInfo);

    Product setMetaKeys(String metaKeys);

    Product setLastChangeDate(Timestamp lastChangeDate);

    Product setAddedBy(User addedBy);

    Product setIsUsed(boolean isUsed);

    Product setExtraNotes(String extraNotes);

    Product setVendorCode(String vendorCode);

    Product setGender(Gender gender);

}