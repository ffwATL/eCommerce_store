package com.ffwatl.manage.presenters.items;


import com.ffwatl.manage.entities.currency.Currency;
import com.ffwatl.manage.entities.group.Gender;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.color.Color;
import com.ffwatl.manage.presenters.users.UserGenPresenter;

import java.util.Arrays;
import java.util.List;

public class ItemPresenter {
    /*These parameters used for a common cases*/
    private long id;
    private I18n itemName;
    private int quantity;
    private int originPrice;
    private int salePrice;
    private int discount;
    private boolean active;
    private Color color;
    private Gender gender;
    private ItemGroup itemGroup;
    private I18n description;
    private String extraNotes;
    private Currency currency;

    /*These parameters used in item 'edit' mode*/
    private int[] removedImgs;
    private long[] oldSizes;
    private boolean edit;

    /*These parameters send only to UI*/
    private List<ItemImage> images;
    private List<ItemImage> thumbs;
    private UserGenPresenter addedBy;


    public long getId() {
        return id;
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

    public UserGenPresenter getAddedBy() {
        return addedBy;
    }

    public I18n getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOriginPrice() {
        return originPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isActive() {
        return active;
    }

    public Color getColor() {
        return color;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public List<ItemImage> getImages() {
        return images;
    }

    public List<ItemImage> getThumbs() {
        return thumbs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setItemName(I18n itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOldSizes(long[] oldSizes) {
        this.oldSizes = oldSizes;
    }

    public void setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    public void setThumbs(List<ItemImage> thumbs) {
        this.thumbs = thumbs;
    }

    public void setAddedBy(UserGenPresenter addedBy) {
        this.addedBy = addedBy;
    }

    public void setDescription(I18n description) {
        this.description = description;
    }

    public void setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRemovedImgs(int[] removedImgs) {
        this.removedImgs = removedImgs;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }


    @Override
    public String toString() {
        return "ItemPresenter{" +
                "id=" + id +
                ", itemName=" + itemName +
                ", quantity=" + quantity +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", active=" + active +
                ", color=" + color +
                ", gender=" + gender +
                ", itemGroup=" + itemGroup +
                ", description=" + description +
                ", extraNotes='" + extraNotes + '\'' +
                ", currency=" + currency +
                ", removedImgs=" + Arrays.toString(removedImgs) +
                ", oldSizes=" + Arrays.toString(oldSizes) +
                ", edit=" + edit +
                ", images=" + images +
                ", thumbs=" + thumbs +
                ", addedBy=" + addedBy +
                '}';
    }
}