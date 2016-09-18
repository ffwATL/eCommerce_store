package com.ffwatl.manage.entities.i18n;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.beans.Transient;

@Entity
public final class I18n implements Comparable<I18n> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String locale_en;

    private String locale_ru;

    private String locale_ua;

    public long getId() {
        return id;
    }

    public String getLocale_en() {
        return locale_en;
    }

    public String getLocale_ru() {
        return locale_ru;
    }

    public String getLocale_ua() {
        return locale_ua;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocale_en(String locale_en) {
        this.locale_en = locale_en;
    }

    public void setLocale_ru(String locale_ru) {
        this.locale_ru = locale_ru;
    }

    public void setLocale_ua(String locale_ua) {
        this.locale_ua = locale_ua;
    }

    @Transient
    public String getValue(String locale){
        switch (locale.toLowerCase()){
            case "en": return locale_en;
            case "ru": return locale_ru;
            case "ua": return locale_ua;
            default: return locale_en;
        }
    }

    @Override
    public String toString() {
        return "I18n{" +
                "id=" + id +
                ", locale_en='" + locale_en + '\'' +
                ", locale_ru='" + locale_ru + '\'' +
                ", locale_ua='" + locale_ua + '\'' +
                '}';
    }

    @Override
    public int compareTo(I18n o) {
        if(o == null) return 1;
        return this.locale_en.compareTo(o.getLocale_en());
    }
}