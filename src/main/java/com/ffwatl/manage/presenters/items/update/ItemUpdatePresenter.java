package com.ffwatl.manage.presenters.items.update;


import java.io.Serializable;
import java.util.Map;

public class ItemUpdatePresenter implements Serializable{

    private long [] identifiers;

    private Map<String, String> options;

    public Map<String, String> getOptions() {
        return options;
    }

    public long[] getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(long[] identifiers) {
        this.identifiers = identifiers;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}