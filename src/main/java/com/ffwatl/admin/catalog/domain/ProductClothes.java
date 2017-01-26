package com.ffwatl.admin.catalog.domain;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Table(name = "clothes_items")
public class ProductClothes extends ProductDefault {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProductAttributeImpl.class)
    @Fetch(value = FetchMode.JOIN)
    @Column(nullable = false)
    @JoinColumn(name = "product_attribute_id")
    private List<ProductAttribute> size;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = BrandImpl.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public List<ProductAttribute> getSize() {
        return size;
    }

    public void setSize(List<ProductAttribute> size) {
        this.size = size;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString() +" ProductClothes{" +
                "id=" + getId() +
                ", size=" + size +
                ", brand=" + brand +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductClothes that = (ProductClothes) o;
        return getSize().equals(that.getSize()) && getBrand().equals(that.getBrand());
    }

    @Override
    public int hashCode() {
        int result =  getSize().hashCode();
        result = 31 * result + getBrand().hashCode();
        return result;
    }
}