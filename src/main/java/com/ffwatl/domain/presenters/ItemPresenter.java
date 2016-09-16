package com.ffwatl.domain.presenters;


import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.items.color.Color;

import java.util.List;

public class ItemPresenter {

    private long id;

    private String itemName;

    private int quantity;

    private int originPrice;

    private int salePrice;

    private int discount;

    private boolean active;

    private Color color;

    private ItemGroup itemGroup;

    private List<String> images;

    private List<String> thumbs;


    public long getId() {
        return id;
    }

    public String getItemName() {
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

    public void setItemName(String itemName) {
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

    @Override
    public String toString() {
        return "ItemPresenter{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", discount=" + discount +
                ", active=" + active +
                ", color=" + color +
                ", itemGroup=" + itemGroup.getGroupName() +
                ", images=" + images +
                ", thumbs=" + thumbs +
                '}';
    }
}