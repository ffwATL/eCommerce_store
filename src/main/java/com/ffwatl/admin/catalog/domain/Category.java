package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;

import java.util.List;

public interface Category {

    boolean FETCHED = true;
    boolean NO_CHILD = false;

    long getId();
    int getLevel();
    int getWeight();
    User getCreatedBy();
    CommonCategory getCat();
    List<Category> getChild();
    I18n getGroupName();
    String getDescription();

    Category setId(long id);
    Category setCat(CommonCategory cat);
    Category setGroupName(I18n groupName);
    Category setCreatedBy(User createdBy);
    Category setDescription(String description);
    Category setChild(List<Category> child);
    Category setLevel(int level);
    Category setWeight(int weight);
}