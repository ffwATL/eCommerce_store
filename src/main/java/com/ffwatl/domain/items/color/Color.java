package com.ffwatl.domain.items.color;

import com.ffwatl.domain.i18n.I18n;

import javax.persistence.*;

@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private I18n color;

    @Column(length = 8)
    private String hex;

    private boolean print = false;

    public long getId() {
        return id;
    }

    public String getHex() {
        return hex;
    }

    public boolean isPrint() {
        return print;
    }

    public I18n getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Color setColor(I18n color) {
        this.color = color;
        return this;
    }

    public Color setPrint(boolean print) {
        this.print = print;
        return this;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color=" + color +
                ", hex='" + hex + '\'' +
                ", print=" + print +
                '}';
    }
}