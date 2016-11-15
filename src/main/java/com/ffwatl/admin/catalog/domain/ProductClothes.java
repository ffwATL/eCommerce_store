package com.ffwatl.admin.catalog.domain;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Table(name = "clothes_items")
public class ProductClothes extends ProductDefault {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = SizeImpl.class)
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Size> size;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = BrandImpl.class)
    private Brand brand;

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
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