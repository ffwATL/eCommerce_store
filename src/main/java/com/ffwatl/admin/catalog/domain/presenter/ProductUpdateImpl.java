package com.ffwatl.admin.catalog.domain.presenter;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.CategoryDTO;
import com.ffwatl.admin.catalog.domain.dto.ColorDTO;
import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.dto.UserDTO;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProductUpdateImpl implements ProductUpdate {
    /*These parameters used for a common cases*/
    private long id;
    private I18n itemName;
    private int quantity;
    private int originPrice;
    private int salePrice;
    private int discount;
    private boolean isActive;
    private Gender gender;
    private I18n description;
    private String extraNotes;
    private Currency currency;
    private String vendorCode;
    private String metaInfo;
    private String metaKeys;
    private boolean isUsed;

    @JsonDeserialize(as=ColorDTO.class)
    private Color color;
    @JsonDeserialize(as=UserDTO.class)
    private User addedBy;
    @JsonDeserialize(as=CategoryDTO.class)
    private Category itemGroup;

    private Date importDate;
    private Timestamp lastChangeDate;

    /*These parameters used in item 'edit' mode*/
    private int[] removedImgs;
    private long[] oldSizes;
    private boolean edit;

    /*These parameters send only to UI*/
    private List<ProductImage> images;
    private List<ProductImage> thumbs;


    public long getId() {
        return id;
    }

    public Category getItemGroup() {
        return itemGroup;
    }

    @Override
    public String getVendorCode() {
        return vendorCode;
    }

    @Override
    public String getMetaInfo() {
        return metaInfo;
    }

    @Override
    public String getMetaKeys() {
        return metaKeys;
    }

    @Override
    public boolean isUsed() {
        return isUsed;
    }

    public Date getImportDate() {
        return importDate;
    }

    @Override
    public Timestamp getLastChangeDate() {
        return lastChangeDate;
    }

    @Override
    public Offer getOffer() {
        return null;
    }

    public Currency getCurrency() {
        return currency;
    }

    public long[] getOldSizes() {
        return oldSizes;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isEdit() {
        return edit;
    }

    public int[] getRemovedImgs() {
        return removedImgs;
    }

    public String getExtraNotes() {
        return extraNotes;
    }

    public I18n getDescription() {
        return description;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public I18n getProductName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRetailPrice() {
        return originPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getDiscount() {
        return discount;
    }

    @Override
    public int getOriginPrice() {
        return 0;
    }

    public boolean isActive() {
        return isActive;
    }

    public Color getColor() {
        return color;
    }

    public Category getCategory() {
        return itemGroup;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public List<ProductImage> getThumbs() {
        return thumbs;
    }

    public ProductUpdate setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductUpdate setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    @Override
    public ProductUpdate setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }

    @Override
    public ProductUpdate setMetaKeys(String metaKeys) {
        this.metaKeys = metaKeys;
        return this;
    }

    @Override
    public ProductUpdate setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
        return this;
    }

    @Override
    public ProductUpdate setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
        return this;
    }

    public ProductUpdate setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public ProductUpdate setItemName(I18n itemName) {
        this.itemName = itemName;
        return this;
    }

    public ProductUpdate setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductUpdate setOldSizes(long[] oldSizes) {
        this.oldSizes = oldSizes;
        return this;
    }

    public ProductUpdate setRetailPrice(int originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public ProductUpdate setSalePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public ProductUpdate setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    @Override
    public Product setOriginPrice(int originPrice) {
        return null;
    }

    public ProductUpdate setActive(boolean active) {
        this.isActive = active;
        return this;
    }

    public ProductUpdate setColor(Color color) {
        this.color = color;
        return this;
    }

    public ProductUpdate setItemGroup(Category itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public ProductUpdate setImages(List<ProductImage> images) {
        this.images = images;
        return this;
    }

    public ProductUpdate setThumbs(List<ProductImage> thumbs) {
        this.thumbs = thumbs;
        return this;
    }

    public ProductUpdate setAddedBy(User addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public ProductUpdate setDescription(I18n description) {
        this.description = description;
        return this;
    }

    public ProductUpdate setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }

    public ProductUpdate setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
        return this;
    }

    public ProductUpdate setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Product setOffer(Offer offer) {
        return null;
    }

    public ProductUpdate setRemovedImgs(int[] removedImgs) {
        this.removedImgs = removedImgs;
        return this;
    }

    public ProductUpdate setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }

    @Override
    public String toString() {
        return "ProductUpdateImpl{" +
                "id=" + id +
                ", itemName=" + itemName +
                ", quantity=" + quantity +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", isActive=" + isActive +
                ", gender=" + gender +
                ", description=" + description +
                ", extraNotes='" + extraNotes + '\'' +
                ", currency=" + currency +
                ", vendorCode='" + vendorCode + '\'' +
                ", metaInfo='" + metaInfo + '\'' +
                ", metaKeys='" + metaKeys + '\'' +
                ", isUsed=" + isUsed +
                ", color=" + color +
                ", addedBy=" + addedBy +
                ", itemGroup=" + itemGroup +
                ", importDate=" + importDate +
                ", lastChangeDate=" + lastChangeDate +
                ", removedImgs=" + Arrays.toString(removedImgs) +
                ", oldSizes=" + Arrays.toString(oldSizes) +
                ", edit=" + edit +
                ", images=" + images +
                ", thumbs=" + thumbs +
                '}';
    }
}