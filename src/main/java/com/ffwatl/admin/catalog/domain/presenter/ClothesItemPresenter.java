package com.ffwatl.admin.catalog.domain.presenter;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.BrandDTO;
import com.ffwatl.admin.catalog.domain.dto.SizeDTO;
import com.ffwatl.admin.user.domain.dto.UserDTO;

import java.util.List;

public class ClothesItemPresenter extends ProductUpdateImpl {


    private String brandImgUrl;
    @JsonDeserialize(as=BrandDTO.class)
    private Brand brand;
    @JsonDeserialize(contentAs=SizeDTO.class)
    private List<Size> size;

    public ClothesItemPresenter(){}

    public ClothesItemPresenter(ProductClothes item){
        setId(item.getId());
        setItemName(item.getProductName());
        setQuantity(item.getQuantity());
        setColor(item.getColor());
        setSalePrice(item.getSalePrice());
        setRetailPrice(item.getRetailPrice());
        setDiscount(item.getDiscount());
        setItemGroup(item.getCategory());
        setActive(item.isActive());
        UserDTO user = new UserDTO();
        user.setEmail(item.getAddedBy().getEmail());
        setAddedBy(user);
        setDescription(item.getDescription());
        setExtraNotes(item.getExtraNotes());
        setGender(item.getGender());
        setImportDate(item.getImportDate());
    }

    public ClothesItemPresenter(ProductUpdateImpl presenter){
        setItemName(presenter.getProductName());
        setQuantity(presenter.getQuantity());
        setColor(presenter.getColor());
        setSalePrice(presenter.getSalePrice());
        setRetailPrice(presenter.getRetailPrice());
        setDiscount(presenter.getDiscount());
        setItemGroup(presenter.getCategory());
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