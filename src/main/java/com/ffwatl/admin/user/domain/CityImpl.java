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
    public String toString() {
        return "CityImpl{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
