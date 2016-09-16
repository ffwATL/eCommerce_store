package com.ffwatl.domain.items.clothes.size;

import com.ffwatl.domain.items.field.Field;

import javax.persistence.*;
import java.util.List;

/**
 * @author ffw_ATL
 * Entity class for handling Item size information. It contain such info as:
 * - items which this instance belong to;
 * - quantity of this size;
 * - reference to measurement fields objects where main measurement information is placed.
 */
@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 2, nullable = false)
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Field.class, fetch = FetchType.EAGER)
    private List<Field> measurements;

    @ManyToOne(cascade = CascadeType.MERGE)
    private EuroSize eu_size;

    /**
     * Returns current Size Id;
     * @return size Id;
     */
    public long getId() {
        return id;
    }

    /**
     * Returns EuroSize object. That object contains name of EU size value (S,M or W32 L32 or UK7);
     * @return EuroSize object.
     */
    public EuroSize getEu_size() {
        return eu_size;
    }

    /**
     * Returns quantity of current size;
     * @return current Size quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns list of Field for current size;
     * @return list of Field.
     */
    public List<Field> getMeasurements() {
        return measurements;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMeasurements(List<Field> measurements) {
        this.measurements = measurements;
    }

    public void setEu_size(EuroSize eu_size) {
        this.eu_size = eu_size;
    }

    @Override
    public String toString() {
        return "Size{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return getMeasurements().equals(size.getMeasurements());
    }

    @Override
    public int hashCode() {
        return getMeasurements().hashCode();
    }
}