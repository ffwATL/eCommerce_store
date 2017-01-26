package com.ffwatl.admin.catalog.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author ffw_ATL
 * Entity class for handling Product attribute information (ie size). It contain such info as:
 * - items which this instance belong to;
 * - quantity of this size;
 * - reference to measurement fields objects where main measurement information is placed.
 */
@Entity
@Table(name = "product_attributes")
public class ProductAttributeImpl implements ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2, nullable = false)
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FieldImpl.class, fetch = FetchType.EAGER)
    private List<Field> measurements;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ProductAttributeTypeImpl.class)
    private ProductAttributeType eu_size;

    /**
     * Returns current ProductAttributeImpl Id;
     * @return size Id;
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Returns ProductAttributeTypeImpl object. That object contains name of EU size value (S,M or W32 L32 or UK7);
     * @return ProductAttributeTypeImpl object.
     */
    @Override
    public ProductAttributeType getEu_size() {
        return eu_size;
    }

    /**
     * Returns quantity of current size;
     * @return current ProductAttributeImpl quantity.
     */
    @Override
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns list of FieldImpl for current size;
     * @return list of FieldImpl.
     */
    @Override
    public List<Field> getFields() {
        return measurements;
    }

    @Override
    public ProductAttribute setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductAttribute setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ProductAttribute setFields(List<Field> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Override
    public ProductAttribute setEu_size(ProductAttributeType eu_size) {
        this.eu_size = eu_size;
        return this;
    }

    @Override
    public String toString() {
        return "ProductAttribute{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                ", eu_size=" + eu_size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute size = (ProductAttribute) o;
        return getId() == size.getId();
    }

    @Override
    public int hashCode() {
        return getFields().hashCode();
    }

    @Override
    public int compareTo(ProductAttribute o) {
        if(o == null) return 1;
        return this.eu_size.compareTo(o.getEu_size());
    }
}