package com.ffwatl.common.rule;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferImpl;

import javax.persistence.*;

@Entity
@Table(name = "rules")
public class RuleImpl implements Rule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = OfferImpl.class)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "rule_type")
    private String type; // type of this rule, ie target for which this rule is valid

    @Column(name = "bound_value")
    private String boundValue; // bound value to given fieldName

    @Column(name = "field_name")
    private String fieldName; // field name for which boundValue is applied

    @Column(name = "excluded")
    private boolean excluded; // if true then items with this boundValue will be excluded from the Offer

    @Column(name = "value_type")
    private ValueType fieldType;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getBoundValue() {
        return boundValue;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean isExcluded() {
        return excluded;
    }

    @Override
    public ValueType getFieldType() {
        return fieldType;
    }

    @Override
    public Rule setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Rule setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public Rule setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public Rule setBoundValue(String boundValue) {
        this.boundValue = boundValue;
        return this;
    }

    @Override
    public Rule setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    @Override
    public Rule setExcluded(boolean excluded) {
        this.excluded = excluded;
        return this;
    }

    @Override
    public Rule setFieldType(ValueType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleImpl rule = (RuleImpl) o;

        if (isExcluded() != rule.isExcluded()) return false;
        if (getType() != null ? !getType().equals(rule.getType()) : rule.getType() != null) return false;
        if (getBoundValue() != null ? !getBoundValue().equals(rule.getBoundValue()) : rule.getBoundValue() != null)
            return false;
        if (getFieldName() != null ? !getFieldName().equals(rule.getFieldName()) : rule.getFieldName() != null)
            return false;
        return getFieldType() == rule.getFieldType();

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getBoundValue() != null ? getBoundValue().hashCode() : 0);
        result = 31 * result + (getFieldName() != null ? getFieldName().hashCode() : 0);
        result = 31 * result + (isExcluded() ? 1 : 0);
        result = 31 * result + (getFieldType() != null ? getFieldType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuleImpl{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", boundValue='" + boundValue + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", excluded=" + excluded +
                ", fieldType=" + fieldType +
                '}';
    }
}