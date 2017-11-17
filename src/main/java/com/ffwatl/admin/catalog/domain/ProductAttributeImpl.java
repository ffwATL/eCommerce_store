package com.ffwatl.admin.catalog.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL
 * Entity class for handling Product attribute information (ie size). It contains such info as:
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

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = AttributeNameImpl.class)
    @JoinColumn(name = "attribute_name_id")
    private AttributeName attributeName;

    @Column(length = 2, nullable = false, name = "quantity")
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = FieldImpl.class, fetch = FetchType.LAZY)
    private List<Field> fields = new ArrayList<>();

    @Column(name = "attribute_type")
    private ProductAttributeType productAttributeType;

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

    @Override
    public AttributeName getAttributeName() {
        return attributeName;
    }

    /**
     * Returns ProductAttributeType object. That object contains name of EU size value (S,M or W32 L32 or UK7);
     * @return ProductAttributeType object.
     */
    @Override
    public ProductAttributeType getProductAttributeType() {
        return productAttributeType;
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
        return fields;
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
    public ProductAttribute setAttributeName(AttributeName attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Override
    public ProductAttribute setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public ProductAttribute setFields(List<Field> measurements) {
        this.fields = measurements;
        return this;
    }

    @Override
    public ProductAttribute setProductAttributeType(ProductAttributeType productAttrType) {
        this.productAttributeType = productAttrType;
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
        if (getVersion() != that.getVersion()) return false;
        if (getProduct() != null ? !getProduct().equals(that.getProduct()) : that.getProduct() != null) return false;
        if (getAttributeName() != null ? !getAttributeName().equals(that.getAttributeName()) : that.getAttributeName() != null)
            return false;
        if (getFields() != null ? !getFields().equals(that.getFields()) : that.getFields() != null) return false;
        return getProductAttributeType() == that.getProductAttributeType();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getProduct() != null ? getProduct().hashCode() : 0);
        result = 31 * result + (getAttributeName() != null ? getAttributeName().hashCode() : 0);
        result = 31 * result + (getFields() != null ? getFields().hashCode() : 0);
        result = 31 * result + (getProductAttributeType() != null ? getProductAttributeType().hashCode() : 0);
        result = 31 * result + getVersion();
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeImpl{" +
                "id=" + id +
                ", attributeName=" + attributeName +
                ", quantity=" + quantity +
                ", fields=" + fields +
                ", productAttributeType=" + productAttributeType +
                ", version=" + version +
                '}';
    }

    @Override
    public int compareTo(ProductAttribute o) {
        if(o == null) {
            return 1;
        }
        return this.attributeName.compareTo(o.getAttributeName());
    }
}