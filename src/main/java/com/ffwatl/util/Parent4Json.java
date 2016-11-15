package com.ffwatl.util;

import com.ffwatl.admin.i18n.domain.I18n;



public class Parent4Json {

    private I18n groupName;

    private int level;

    public I18n getGroupName() {
        return groupName;
    }

    public int getLevel() {
        return level;
    }

    public void setGroupName(I18n groupName) {
        this.groupName = groupName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Parent4Json{" +
                "groupName=" + groupName +
                ", level=" + level +
                '}';
    }
}