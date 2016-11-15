package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "eu_size")
public class EuroSizeImpl implements EuroSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n name;

    private CommonCategory cat;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public CommonCategory getCat() {
        return cat;
    }

    @Override
    public I18n getName() {
        return name;
    }

    @Override
    public EuroSize setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public EuroSize setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public EuroSize setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "EuroSizeImpl{" +
                "id=" + id +
                ", name=" + name +
                ", cat=" + cat +
                '}';
    }

    @Override
    public int compareTo(EuroSize o) {
        if(o == null) return 1;
        if(this.id < o.getId()) return -1;
        return this.id == o.getId() ? 0 : 1;
    }

}