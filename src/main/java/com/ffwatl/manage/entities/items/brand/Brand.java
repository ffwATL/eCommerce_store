package com.ffwatl.manage.entities.items.brand;


import com.ffwatl.manage.entities.i18n.I18n;

import javax.persistence.*;

@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private I18n description;

    @Column(name = "image_url", unique = true)
    private String imageURL;

    public long getId() {
        return id;
    }

    public I18n getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(I18n description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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