package com.ffwatl.domain.items.clothes;


import com.ffwatl.domain.group.Gender;
import com.ffwatl.domain.items.brand.Brand;
import com.ffwatl.domain.items.clothes.size.Size;
import com.ffwatl.domain.items.Item;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
public class ClothesItem extends Item {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private List<Size> size;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Brand brand;

    private Gender gender;

    public Gender getGender() {
        return gender;
    }

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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "ClothesItem{" +
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