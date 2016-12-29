package com.ffwatl.admin.i18n.domain;


import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;

@Embeddable
public class I18n implements Serializable, Comparable<I18n> {

    private static final long serialVersionUID = 1L;

    @Column(length = 2048)
    private String locale_en;

    @Column(length = 2048)
    private String locale_ru;

    @Column(length = 2048)
    private String locale_ua;

    public String getLocale_en() {
        return locale_en;
    }

    public String getLocale_ru() {
        return locale_ru;
    }

    public String getLocale_ua() {
        return locale_ua;
    }

    public I18n setLocale_en(String locale_en) {
        this.locale_en = locale_en;
        return this;
    }

    public I18n setLocale_ru(String locale_ru) {
        this.locale_ru = locale_ru;
        return this;
    }

    public I18n setLocale_ua(String locale_ua) {
        this.locale_ua = locale_ua;
        return this;
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