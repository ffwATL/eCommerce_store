package com.ffwatl.service.i18n;


import com.ffwatl.admin.entities.i18n.I18n;

public interface I18nService {

    void save(I18n t);

    I18n findByEn(String name);

    void removeByEn(String name);
}
