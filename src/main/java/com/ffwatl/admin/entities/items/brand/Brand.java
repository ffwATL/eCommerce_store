package com.ffwatl.admin.entities.items.brand;


import com.ffwatl.admin.entities.i18n.I18n;

import javax.persistence.*;

@Entity
@Table(name = "brand")
public class Brand implements IBrand{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private I18n description;


    @Override
    public long getId() {
        return id;
    }
    @Override
    public I18n getDescription() {
        return description;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public IBrand setId(long id) {
        this.id = id;
        return this;
    }
    @Override
    public IBrand setDescription(I18n description) {
        this.description = description;
        return this;
    }
    @Override
    public IBrand setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return getName().equals(brand.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}