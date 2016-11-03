package com.ffwatl.admin.entities.items.clothes.size;


import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.CommonCategory;

import javax.persistence.*;

@Entity
@Table(name = "eu_size")
public class EuroSize implements Comparable<EuroSize>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n name;

    private CommonCategory cat;

    public long getId() {
        return id;
    }

    public CommonCategory getCat() {
        return cat;
    }

    public I18n getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EuroSize setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    public EuroSize setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "EuroSize{" +
                "id=" + id +
                ", name=" + name +
                ", cat=" + cat +
                '}';
    }

    @Override
    public int compareTo(EuroSize o) {
        if(o == null) return 1;
        if(this.id < o.id) return -1;
        return this.id == o.id ? 0 : 1;
    }

}