package com.ffwatl.admin.catalog.domain.presenter;

import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.catalog.domain.ProductDefault;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CatalogItem {

    private String photoUrl;
    private Product item;
    private String thumbnail_url;
    private List<ProductAttribute> size;

    public CatalogItem(ProductDefault item, String photoUrl) {
        if(item == null) throw  new IllegalArgumentException();
        this.item = item;
        this.photoUrl = photoUrl;
        this.thumbnail_url = getThumbnailUrl();
        if(item instanceof ProductClothes) this.size = ((ProductClothes) item).getSize();
    }

    public List<ProductAttribute> getSize() {
        return size;
    }

    public long getId() {
        return item.getId();
    }

    public Currency getCurrency() {
        return item.getCurrency();
    }

    public Category getItemGroup() {
        return item.getCategory();
    }

    public I18n getItemName() {
        return item.getProductName();
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
        return item.getRetailPrice();
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