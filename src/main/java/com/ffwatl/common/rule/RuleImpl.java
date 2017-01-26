package com.ffwatl.common.rule;


import javax.persistence.*;

@Entity
@Table(name = "rules")
public class RuleImpl implements Rule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "rule_type")
    private String type; // type of this rule, ie target for which this rule is valid

    @Column(name = "bound_value")
    private String boundValue; // bound value to given fieldName

    @Column(name = "field_name")
    private String fieldName; // field name for which boundValue is applied

    @Override
    public long getId() {
        return id;
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
    public Rule setId(long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleImpl ruleImpl = (RuleImpl) o;

        if (getType() != null ? !getType().equals(ruleImpl.getType()) : ruleImpl.getType() != null) return false;
        if (getBoundValue() != null ? !getBoundValue().equals(ruleImpl.getBoundValue()) : ruleImpl.getBoundValue() != null)
            return false;
        return !(getFieldName() != null ? !getFieldName().equals(ruleImpl.getFieldName()) : ruleImpl.getFieldName() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getBoundValue() != null ? getBoundValue().hashCode() : 0);
        result = 31 * result + (getFieldName() != null ? getFieldName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuleImpl{" +
                "type='" + type + '\'' +
                ", boundValue='" + boundValue + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}