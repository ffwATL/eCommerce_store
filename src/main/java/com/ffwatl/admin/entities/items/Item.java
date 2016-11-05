package com.ffwatl.admin.entities.items;


import com.ffwatl.admin.entities.currency.Currency;
import com.ffwatl.admin.entities.group.Gender;
import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.color.Color;
import com.ffwatl.admin.entities.users.IUser;

import java.sql.Timestamp;
import java.util.Date;

public interface Item {

    long getId();
    I18n getItemName();
    I18n getDescription();
    IGroup getItemGroup();
    IUser getAddedBy();
    Gender getGender();
    String getExtraNotes();
    String getVendorCode();
    boolean isUsed();
    String getMetaInfo();
    String getMetaKeys();
    Currency getCurrency();
    Color getColor();
    int getQuantity();
    int getDiscount();
    int getOriginPrice();
    int getSalePrice();
    boolean isActive();
    Date getImportDate();
    Timestamp getLastChangeDate();

    Item setId(long id);
    Item setDescription(I18n description);
    Item setCurrency(Currency currency);
    Item setItemGroup(IGroup itemGroup);
    Item setItemName(I18n itemName);
    Item setQuantity(int quantity);
    Item setColor(Color color);
    Item setDiscount(int discount);
    Item setOriginPrice(int originPrice);
    Item setSalePrice(int salePrice);
    Item setIsActive(boolean isActive);
    Item setImportDate(Date importDate);
    Item setMetaInfo(String metaInfo);
    Item setMetaKeys(String metaKeys);
    Item setLastChangeDate(Timestamp lastChangeDate);
    Item setAddedBy(IUser addedBy);
    Item setIsUsed(boolean isUsed);
    Item setExtraNotes(String extraNotes);
    Item setVendorCode(String vendorCode);
    Item setGender(Gender gender);

}