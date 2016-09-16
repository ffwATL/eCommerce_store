package com.ffwatl.domain.presenters;

import com.ffwatl.domain.currency.Currency;
import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.items.Item;
import com.ffwatl.domain.items.clothes.ClothesItem;
import com.ffwatl.domain.items.clothes.size.Size;
import com.ffwatl.domain.items.color.Color;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ItemCatalog {

    private static final String PHOTO_URL = "/storage/items/images/";

    private Item item;
    private String thumbnail_url;
    private List<Size> size;

    public ItemCatalog(Item item) throws IOException {
        if(item == null) throw  new IllegalArgumentException();
        this.item = item;
        this.thumbnail_url = getThumbnailUrl();
        if(item instanceof ClothesItem) this.size = ((ClothesItem) item).getSize();
    }

    public List<Size> getSize() {
        return size;
    }

    public long getId() {
        return item.getId();
    }

    public Currency getCurrency() {
        return item.getCurrency();
    }

    public ItemGroup getItemGroup() {
        return item.getItemGroup();
    }

    public String getItemName() {
        return item.getItemName();
    }

    public int getQuantity() {
        return item.getQuantity();
    }

    public Color getColor() {
        return item.getColor();
    }

    public int getDiscount() {
        return item.getDiscount();
    }

    public int getOriginPrice() {
        return item.getOriginPrice();
    }

    public int getSalePrice() {
        return item.getSalePrice();
    }

    public boolean isActive() {
        return item.isActive();
    }

    public Date getImportDate() {
        return item.getImportDate();
    }

    public Timestamp getLastChangeDate() {
        return item.getLastChangeDate();
    }

    public String getThumbnailUrl() throws IOException {
        if(this.thumbnail_url != null) return this.thumbnail_url;
        this.thumbnail_url = PHOTO_URL + "item_" + getId() + "/image1l.jpg";
        return this.thumbnail_url;
    }
}