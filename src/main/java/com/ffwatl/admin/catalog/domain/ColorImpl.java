package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "color")
public class ColorImpl implements Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n color;

    @Column(name = "hex", length = 30)
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorImpl color1 = (ColorImpl) o;

        if (getId() != color1.getId()) return false;
        if (getColor() != null ? !getColor().equals(color1.getColor()) : color1.getColor() != null) return false;
        return !(getHex() != null ? !getHex().equals(color1.getHex()) : color1.getHex() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + (getHex() != null ? getHex().hashCode() : 0);
        return result;
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