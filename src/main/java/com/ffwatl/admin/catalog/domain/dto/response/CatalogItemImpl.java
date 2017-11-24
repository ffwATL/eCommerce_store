package com.ffwatl.admin.catalog.domain.dto.response;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CatalogItemImpl implements CatalogItem {

    private String photoUrl;
    private Product item;
    private String thumbnail_url;
    private List<ProductAttribute> size;

    public CatalogItemImpl(Product item, String photoUrl) {
        if(item == null) throw  new IllegalArgumentException();
        this.item = item;
        this.photoUrl = photoUrl;
        this.thumbnail_url = getThumbnailUrl();
        /*if(item instanceof ProductClothes) this.size = ((ProductClothes) item).getSize();*/
    }

    @Override
    public List<ProductAttribute> getSize() {
        return size;
    }

    @Override
    public long getId() {
        return item.getId();
    }

    @Override
    public Currency getCurrency() {
        return item.getCurrency();
    }

    @Override
    public ProductCategory getItemGroup() {
        return item.getProductCategory();
    }

    @Override
    public I18n getItemName() {
        return item.getProductName();
    }

    @Override
    public int getQuantity() {
        return item.getQuantity();
    }

    @Override
    public Color getColor() {
        return /*item.getColorName()*/ null;
    }

    @Override
    public int getOriginPrice() {
        return item.getRetailPrice();
    }

    @Override
    public int getSalePrice() {
        return item.getSalePrice();
    }

    @Override
    public boolean isActive() {
        return item.isActive();
    }

    @Override
    public Date getImportDate() {
        return item.getImportDate();
    }

    @Override
    public Timestamp getLastChangeDate() {
        return item.getLastChangeDate();
    }

    @Override
    public String getThumbnailUrl() {
        if(this.thumbnail_url != null) return this.thumbnail_url;
        this.thumbnail_url = photoUrl + "item_" + getId() + "/image1l.jpg";
        return this.thumbnail_url;
    }
}