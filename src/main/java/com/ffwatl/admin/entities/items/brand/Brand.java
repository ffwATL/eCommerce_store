package com.ffwatl.admin.entities.items.brand;


import com.ffwatl.admin.entities.i18n.I18n;

import javax.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private I18n description;

    public long getId() {
        return id;
    }

    public I18n getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(I18n description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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