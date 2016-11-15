package com.ffwatl.admin.user.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "countries")
public class CountryImpl implements Country {

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
    public Country setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Country setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}