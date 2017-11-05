package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

/**
 * @author ffw_ATL.
 */
public interface AttributeName extends Comparable<AttributeName> {

    long getId();

    I18n getName();

    AttributeName setId(long id);

    AttributeName setName(I18n name);
}