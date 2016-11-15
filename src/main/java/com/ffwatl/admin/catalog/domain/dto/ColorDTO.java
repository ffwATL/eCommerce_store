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
}
