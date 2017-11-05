package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import java.util.List;
import java.util.Set;

/**
 * @author ffw_ATL.
 */
public interface ProductAttributeTemplate {

    long getId();

    I18n getTemplateName();

    List<Field> getFields();

    Set<AttributeName> getAttributeNames();


    ProductAttributeTemplate setId(long id);

    ProductAttributeTemplate setTemplateName(I18n name);

    ProductAttributeTemplate setFields(List<Field> fields);

    ProductAttributeTemplate setAttributeNames(Set<AttributeName> attributeNames);
}
