package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.i18n.domain.I18n;


public class ColorDTO implements Color {

    private long id;

    private I18n color;

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
    public I18n getColorName() {
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
    public Color setColorName(I18n color) {
        this.color = color;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorDTO colorDTO = (ColorDTO) o;

        if (getId() != colorDTO.getId()) return false;
        if (getColorName() != null ? !getColorName().equals(colorDTO.getColorName()) : colorDTO.getColorName() != null) return false;
        return !(getHex() != null ? !getHex().equals(colorDTO.getHex()) : colorDTO.getHex() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getColorName() != null ? getColorName().hashCode() : 0);
        result = 31 * result + (getHex() != null ? getHex().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ColorDTO{" +
                "id=" + id +
                ", color=" + color +
                ", hex='" + hex + '\'' +
                '}';
    }
}