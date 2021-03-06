package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;



public interface Brand {

    long getId();

    I18n getDescription();

    String getName();

    Brand setId(long id);

    Brand setDescription(I18n description);

    Brand setName(String name);
}