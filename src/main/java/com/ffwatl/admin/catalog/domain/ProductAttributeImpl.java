package com.ffwatl.admin.catalog.domain;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ProductImpl.class)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 2, nullable = false, name = "quantity")
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FieldImpl.class, fetch = FetchType.LAZY)
    private List<Field> measurements = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ProductAttributeTypeImpl.class)
    @JoinColumn(name = "product_attr_type_id")
    private ProductAttributeType eu_size;

    @Column(name = "version")
    @Version
    private int version;

    /**
     * Returns current ProductAttributeImpl Id;
     * @return size Id;
     */
    @Override
    public long getId() {
        return id;
    }

    @Override
    public Product getProduct() {
        return product;
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
    public int getVersion() {
        return version;
    }

    @Override
    public ProductAttribute setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductAttribute setProduct(Product product) {
        this.product = product;
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
    public ProductAttribute setVersion(int version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributeImpl that = (ProductAttributeImpl) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        /*if (getVersion() != that.getVersion()) return false;*/
        if (measurements != null ? !measurements.equals(that.measurements) : that.measurements != null) return false;
        return !(getEu_size() != null ? !getEu_size().equals(that.getEu_size()) : that.getEu_size() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getQuantity();
        result = 31 * result + (measurements != null ? measurements.hashCode() : 0);
        result = 31 * result + (getEu_size() != null ? getEu_size().hashCode() : 0);
        /*result = 31 * result + getVersion();*/
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeImpl{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                ", eu_size=" + eu_size +
                ", version=" + version +
                '}';
    }

    @Override
    public int compareTo(ProductAttribute o) {
        if(o == null) return 1;
        return this.eu_size.compareTo(o.getEu_size());
    }
}