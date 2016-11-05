package com.ffwatl.admin.entities.items.clothes;


import com.ffwatl.admin.entities.items.DefaultItem;
import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.brand.IBrand;
import com.ffwatl.admin.entities.items.clothes.size.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Table(name = "clothes_items")
public class ClothesItem extends DefaultItem {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Size> size;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = Brand.class)
    private IBrand brand;

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public IBrand getBrand() {
        return brand;
    }

    public void setBrand(IBrand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString() +" ClothesItem{" +
                "id=" + getId() +
                ", size=" + size +
                ", brand=" + brand +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothesItem that = (ClothesItem) o;
        return getSize().equals(that.getSize()) && getBrand().equals(that.getBrand());
    }

    @Override
    public int hashCode() {
        int result =  getSize().hashCode();
        result = 31 * result + getBrand().hashCode();
        return result;
    }
}