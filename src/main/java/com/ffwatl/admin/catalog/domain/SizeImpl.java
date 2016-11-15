package com.ffwatl.admin.catalog.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author ffw_ATL
 * Entity class for handling Product size information. It contain such info as:
 * - items which this instance belong to;
 * - quantity of this size;
 * - reference to measurement fields objects where main measurement information is placed.
 */
@Entity
@Table(name = "size")
public class SizeImpl implements Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2, nullable = false)
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FieldImpl.class, fetch = FetchType.EAGER)
    private List<Field> measurements;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = EuroSizeImpl.class)
    private EuroSize eu_size;

    /**
     * Returns current SizeImpl Id;
     * @return size Id;
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Returns EuroSizeImpl object. That object contains name of EU size value (S,M or W32 L32 or UK7);
     * @return EuroSizeImpl object.
     */
    @Override
    public EuroSize getEu_size() {
        return eu_size;
    }

    /**
     * Returns quantity of current size;
     * @return current SizeImpl quantity.
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
    public List<Field> getMeasurements() {
        return measurements;
    }

    @Override
    public Size setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Size setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Size setMeasurements(List<Field> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Override
    public Size setEu_size(EuroSize eu_size) {
        this.eu_size = eu_size;
        return this;
    }

    @Override
    public String toString() {
        return "Size{" +
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
        Size size = (Size) o;
        return getId() == size.getId();
    }

    @Override
    public int hashCode() {
        return getMeasurements().hashCode();
    }

    @Override
    public int compareTo(Size o) {
        if(o == null) return 1;
        return this.eu_size.compareTo(o.getEu_size());
    }
}