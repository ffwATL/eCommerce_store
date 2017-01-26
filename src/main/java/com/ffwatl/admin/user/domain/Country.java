package com.ffwatl.admin.user.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import java.io.Serializable;


public interface Country extends Serializable{

    long getId();

    I18n getName();

    Country setName(I18n name);

    Country setId(long id);
}