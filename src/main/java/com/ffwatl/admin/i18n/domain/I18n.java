package com.ffwatl.admin.i18n.domain;


import javax.persistence.*;
import java.beans.Transient;

@Embeddable
public class I18n implements Comparable<I18n> {

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