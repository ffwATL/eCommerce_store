package com.ffwatl.domain.presenters;


import com.ffwatl.domain.items.brand.Brand;
import com.ffwatl.domain.items.clothes.size.Size;

import java.util.List;

public class ClothesItemPresenter extends ItemPresenter{

    private Brand brand;

    private List<Size> size;

    public ClothesItemPresenter(){}

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

    @Override
    public String toString() {
        return "ClothesItemPresenter{" +
                "brand=" + brand +
                ", size=" + size +
                '}';
    }
}