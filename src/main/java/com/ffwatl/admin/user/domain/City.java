package com.ffwatl.admin.user.domain;

import com.ffwatl.admin.i18n.domain.I18n;



public interface City {
    long getId();
    I18n getName();

    City setId(long id);
    City setName(I18n name);
}