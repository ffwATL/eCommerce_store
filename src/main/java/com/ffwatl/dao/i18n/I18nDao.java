package com.ffwatl.dao.i18n;

import com.ffwatl.admin.entities.i18n.I18n;

import java.util.List;



public interface I18nDao {

    List<I18n> findByEn(String en);

    void save(I18n t);

    void remove(I18n t);


}
