package com.ffwatl.domain.users.address;


import com.ffwatl.domain.i18n.I18n;

import javax.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private I18n name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public I18n getName() {
        return name;
    }

    public void setName(I18n name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}