package com.ffwatl.admin.user.domain;


import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class CityImpl implements City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
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

        CityImpl city = (CityImpl) o;

        if (getId() != city.getId()) return false;
        return !(getName() != null ? !getName().equals(city.getName()) : city.getName() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityImpl{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}