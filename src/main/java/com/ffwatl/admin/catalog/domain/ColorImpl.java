package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "colors")
public class ColorImpl implements Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n color;

    @Column(length = 30)
    private String hex;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getHex() {
        return hex;
    }

    @Override
    public I18n getColor() {
        return color;
    }

    @Override
    public Color setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Color setHex(String hex) {
        this.hex = hex;
        return this;
    }

    @Override
    public Color setColor(I18n color) {
        this.color = color;
        return this;
    }

    @Override
    public String toString() {
        return "ColorImpl{" +
                "id=" + id +
                ", color=" + color +
                ", hex='" + hex + '\'' +
                '}';
    }
}