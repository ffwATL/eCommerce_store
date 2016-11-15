package com.ffwatl.admin.user.domain.dto;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.City;

public class CityDTO implements City{

    private long id;
    private I18n name;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public I18n getName() {
        return name;
    }

    @Override
    public City setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public City setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}