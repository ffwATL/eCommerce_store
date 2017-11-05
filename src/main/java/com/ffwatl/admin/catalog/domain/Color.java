package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;


public interface Color {

    long getId();

    String getHex();

    I18n getColorName();

    Color setId(long id);

    Color setHex(String hex);

    Color setColorName(I18n color);
}