package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;


public interface EuroSize extends Comparable<EuroSize> {
    long getId();

    CommonCategory getCat();

    I18n getName();

    EuroSize setId(long id);

    EuroSize setCat(CommonCategory cat);

    EuroSize setName(I18n name);
}
