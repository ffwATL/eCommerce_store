package com.ffwatl.admin.presenters.items;

import com.ffwatl.admin.entities.currency.Currency;
import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.Item;
import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import com.ffwatl.admin.entities.items.clothes.size.Size;
import com.ffwatl.admin.entities.items.color.Color;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ItemCatalog {

    private String photoUrl;
    private Item item;
    private String thumbnail_url;
    private List<Size> size;

    public ItemCatalog(Item item, String photoUrl) {
        if(item == null) throw  new IllegalArgumentException();
        this.item = item;
        this.photoUrl = photoUrl;
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

    public I18n getItemName() {
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

    public String getThumbnailUrl() {
        if(this.thumbnail_url != null) return this.thumbnail_url;
        this.thumbnail_url = photoUrl + "item_" + getId() + "/image1l.jpg";
        return this.thumbnail_url;
    }
}