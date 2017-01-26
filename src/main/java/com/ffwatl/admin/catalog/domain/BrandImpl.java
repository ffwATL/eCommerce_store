package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "brand")
public class BrandImpl implements Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "brand_name", unique = true)
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
    public Brand setId(long id) {
        this.id = id;
        return this;
    }
    @Override
    public Brand setDescription(I18n description) {
        this.description = description;
        return this;
    }
    @Override
    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "BrandImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandImpl brand = (BrandImpl) o;
        return getName().equals(brand.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}