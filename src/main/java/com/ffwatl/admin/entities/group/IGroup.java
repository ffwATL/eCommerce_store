package com.ffwatl.admin.entities.group;


import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.users.IUser;

import java.util.List;

public interface IGroup {

    boolean FETCHED = true;
    boolean NO_CHILD = false;

    long getId();
    int getLevel();
    int getWeight();
    IUser getCreatedBy();
    CommonCategory getCat();
    List<IGroup> getChild();
    I18n getGroupName();
    String getDescription();

    IGroup setId(long id);
    IGroup setCat(CommonCategory cat);
    IGroup setGroupName(I18n groupName);
    IGroup setCreatedBy(IUser createdBy);
    IGroup setDescription(String description);
    IGroup setChild(List<IGroup> child);
    IGroup setLevel(int level);
    IGroup setWeight(int weight);
}