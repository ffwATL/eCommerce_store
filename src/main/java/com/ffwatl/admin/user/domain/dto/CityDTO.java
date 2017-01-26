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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityDTO cityDTO = (CityDTO) o;

        if (getId() != cityDTO.getId()) return false;
        return !(getName() != null ? !getName().equals(cityDTO.getName()) : cityDTO.getName() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}