package com.ffwatl.domain.items.clothes.size;


import com.ffwatl.domain.i18n.I18n;
import com.ffwatl.domain.items.CommonCategory;

import javax.persistence.*;

@Entity
@Table(name = "eu_size")
public class EuroSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
}