package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.order.service.FulfillmentType;

import javax.persistence.*;

@Entity
@Table(name = "fulfillment_options")
@NamedQueries({
        @NamedQuery(name = "find_all_fulfillment_options", query = "SELECT f FROM FulfillmentOptionImpl f"),
        @NamedQuery(name = "find_all_fulfillment_options_by_type", query = "SELECT f FROM FulfillmentOptionImpl f " +
                "WHERE f.fulfillmentType=:fulfillmentType")
})
public class FulfillmentOptionImpl implements FulfillmentOption{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_1")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_1")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_1"))
    })
    private I18n name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_2")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_2")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_2"))
    })
    private I18n longDescription;

    @Column(name = "fulfillment_type", nullable = false)
    private String fulfillmentType;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public I18n getName() {
        return name;
    }

    @Override
    public I18n getLongDescription() {
        return longDescription;
    }

    @Override
    public FulfillmentType getFulfillmentType() {
        return FulfillmentType.getInstance(fulfillmentType);
    }

    @Override
    public FulfillmentOption setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public FulfillmentOption setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public FulfillmentOption setLongDescription(I18n longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    @Override
    public FulfillmentOption setFulfillmentType(FulfillmentType fulfillmentType) {
        this.fulfillmentType = fulfillmentType == null ? null : fulfillmentType.getType();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentOptionImpl that = (FulfillmentOptionImpl) o;

        if (getId() != that.getId()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getLongDescription() != null ? !getLongDescription().equals(that.getLongDescription()) : that.getLongDescription() != null)
            return false;
        return !(getFulfillmentType() != null ? !getFulfillmentType().equals(that.getFulfillmentType()) : that.getFulfillmentType() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getLongDescription() != null ? getLongDescription().hashCode() : 0);
        result = 31 * result + (getFulfillmentType() != null ? getFulfillmentType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentOptionImpl{" +
                "id=" + id +
                ", name=" + name +
                ", longDescription=" + longDescription +
                ", fulfillmentType='" + fulfillmentType + '\'' +
                '}';
    }
}