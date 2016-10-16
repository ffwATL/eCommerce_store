package com.ffwatl.manage.presenters.items;


import com.ffwatl.manage.entities.group.Gender;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.color.Color;
import com.ffwatl.manage.presenters.users.UserGenPresenter;

import java.util.List;

public class ItemPresenter {

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

    private List<String> images;

    private List<String> thumbs;

    private UserGenPresenter addedBy;

    private I18n description;

    private String extraNotes;


    public long getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
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

    public List<String> getImages() {
        return images;
    }

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItemName(I18n itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setThumbs(List<String> thumbs) {
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
                ", itemGroup=" + itemGroup +
                ", images=" + images +
                ", thumbs=" + thumbs +
                ", addedBy=" + addedBy +
                ", description=" + description +
                ", extraNotes='" + extraNotes + '\'' +
                '}';
    }
}