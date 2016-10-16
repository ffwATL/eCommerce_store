package com.ffwatl.manage.entities.items.clothes;


import com.ffwatl.manage.entities.items.Item;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.clothes.size.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Table(name = "clothes_items")
public class ClothesItem extends Item {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Size> size;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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