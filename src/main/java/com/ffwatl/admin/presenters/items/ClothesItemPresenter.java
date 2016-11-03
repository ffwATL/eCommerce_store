package com.ffwatl.admin.presenters.items;


import java.util.List;

import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import com.ffwatl.admin.entities.items.clothes.size.Size;
import com.ffwatl.admin.presenters.users.UserGenPresenter;

public class ClothesItemPresenter extends ItemPresenter{

    private Brand brand;
    private String brandImgUrl;
    private List<Size> size;

    public ClothesItemPresenter(){}

    public ClothesItemPresenter(ClothesItem item){
        setId(item.getId());
        setItemName(item.getItemName());
        setQuantity(item.getQuantity());
        setColor(item.getColor());
        setSalePrice(item.getSalePrice());
        setOriginPrice(item.getOriginPrice());
        setDiscount(item.getDiscount());
        setItemGroup(item.getItemGroup());
        setActive(item.isActive());
        UserGenPresenter user = new UserGenPresenter();
        user.setEmail(item.getAddedBy().getEmail());
        setAddedBy(user);
        setDescription(item.getDescription());
        setExtraNotes(item.getExtraNotes());
        setGender(item.getGender());
        setImportDate(item.getImportDate());
    }

    public ClothesItemPresenter(ItemPresenter presenter){
        setItemName(presenter.getItemName());
        setQuantity(presenter.getQuantity());
        setColor(presenter.getColor());
        setSalePrice(presenter.getSalePrice());
        setOriginPrice(presenter.getOriginPrice());
        setDiscount(presenter.getDiscount());
        setItemGroup(presenter.getItemGroup());
        setActive(presenter.isActive());
        setId(presenter.getId());
        setImages(presenter.getImages());
        setThumbs(presenter.getThumbs());
        setAddedBy(presenter.getAddedBy());
        setDescription(presenter.getDescription());
        setExtraNotes(presenter.getExtraNotes());
        setGender(presenter.getGender());
        setImportDate(presenter.getImportDate());
    }

    public String getBrandImgUrl() {
        return brandImgUrl;
    }

    public Brand getBrand() {
        return brand;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public void setBrandImgUrl(String brandImgUrl) {
        this.brandImgUrl = brandImgUrl;
    }

    @Override
    public String toString() {
        return super.toString()+" ClothesItemPresenter{" +
                "brand=" + brand +
                ", size=" + size +
                '}';
    }
}