package com.ffwatl.manage.entities.items.color;

import com.ffwatl.manage.entities.i18n.I18n;

import javax.persistence.*;

@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n color;

    @Column(length = 30)
    private String hex;

    public long getId() {
        return id;
    }

    public String getHex() {
        return hex;
    }

    public I18n getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Color setColor(I18n color) {
        this.color = color;
        return this;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color=" + color +
                ", hex='" + hex + '\'' +
                '}';
    }
}