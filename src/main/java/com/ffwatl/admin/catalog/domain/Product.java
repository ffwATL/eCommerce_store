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

    Boolean isUsed();

    String getMetaInfo();

    String getMetaKeys();

    Currency getCurrency();

    Color getColor();

    Integer getQuantity();

    Integer getOriginPrice();

    Integer getRetailPrice();

    Integer getSalePrice();

    Boolean isActive();

    Date getImportDate();

    Timestamp getLastChangeDate();

    Boolean getCanSellWithoutOptions();

    Gender getGender();

    Integer getNumberOfImages();


    Product setId(long id);

    Product setProductAttributes(List<ProductAttribute> productAttributes);

    Product setBrand(Brand brand);

    Product setDescription(I18n description);

    Product setCurrency(Currency currency);

    Product setProductCategory(ProductCategory productCategory);

    Product setProductName(I18n itemName);

    Product setColor(Color color);

    Product setOriginPrice(Integer originPrice);

    Product setRetailPrice(Integer originPrice);

    Product setSalePrice(Integer salePrice);

    Product setActive(Boolean isActive);

    Product setImportDate(Date importDate);

    Product setMetaInfo(String metaInfo);

    Product setMetaKeys(String metaKeys);

    Product setLastChangeDate(Timestamp lastChangeDate);

    Product setAddedBy(User addedBy);

    Product setIsUsed(Boolean isUsed);

    Product setExtraNotes(String extraNotes);

    Product setVendorCode(String vendorCode);

    Product setCanSellWithoutOptions(Boolean canSellWithoutOptions);

    Product setGender(Gender gender);

    Product setNumberOfImages(Integer numberOfImages);

}