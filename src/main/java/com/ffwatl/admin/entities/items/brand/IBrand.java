package com.ffwatl.admin.entities.items.brand;

import com.ffwatl.admin.entities.i18n.I18n;

/**
 * Created by ffw_ATL on 04-Nov-16.
 */
public interface IBrand {

    long getId();
    I18n getDescription();
    String getName();

    IBrand setId(long id);
    IBrand setDescription(I18n description);
    IBrand setName(String name);
}