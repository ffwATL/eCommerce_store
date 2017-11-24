package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author ffw_ATL
 */
@Entity
@Table(name = "product")
public class ProductImpl implements Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = ProductAttributeImpl.class,
            mappedBy = "product")
    @Column(nullable = false)
    private List<ProductAttribute> productAttributes;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = BrandImpl.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ProductCategoryImpl.class)
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "p_name_en")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "p_name_ru")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "p_name_ua"))
    })
    private I18n productName;

    @Column(name = "vendor_code")
    private String vendorCode;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ColorImpl.class, optional = false)
    @JoinColumn(name = "color_id")
    private Color color;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "desc_en")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "desc_ru")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "desc_ua"))
    })
    private I18n description;

    @Column(name = "extra_notes", length = 2048)
    private String extraNotes;

    @Column(name = "meta_info", length = 1024)
    private String metaInfo;

    @Column(name = "meta_keys")
    private String metaKeys;

    @Column(name = "origin_price")
    private Integer originPrice;

    @Column(name = "retail_price")
    private Integer retailPrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "currency")
    private Currency currency;


    @Column(name = "active")
    private Boolean isActive;

    @Column(name = "used")
    private Boolean isUsed;

    @Column(name = "import_dt", nullable = false)
    private Date importDate;

    @Column(name = "last_change_dt", nullable = false)
    private Timestamp lastChangeDate;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @Column(name = "can_sell_without_attributes")
    private Boolean canSellWithoutOptions = false;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "number_of_images")
    private Integer numberOfImages;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    @Override
    public Brand getBrand() {
        return brand;
    }

    @Override
    public User getAddedBy() {
        return addedBy;
    }

    @Override
    public String getExtraNotes() {
        return extraNotes;
    }

    @Override
    public String getVendorCode() {
        return vendorCode;
    }

    @Override
    public Boolean isUsed() {
        return isUsed;
    }

    @Override
    public I18n getDescription() {
        return description;
    }

    @Override
    public String getMetaInfo() {
        return metaInfo;
    }
    @Override
    public String getMetaKeys() {
        return metaKeys;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @Override
    public I18n getProductName() {
        return productName;
    }

    @Override
    public Integer getQuantity() {
        Integer quantity = 0;
        if(productAttributes != null){
            for(ProductAttribute pa: productAttributes){
                quantity += pa.getQuantity();
            }
        }
        return quantity;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Integer getOriginPrice() {
        return originPrice;
    }

    @Override
    public Integer getRetailPrice() {
        return retailPrice;
    }
    @Override
    public Integer getSalePrice() {
        return salePrice;
    }
    @Override
    public Boolean isActive() {
        return isActive;
    }
    @Override
    public Date getImportDate() {
        return importDate;
    }
    @Override
    public Timestamp getLastChangeDate() {
        return lastChangeDate;
    }

    @Override
    public Boolean getCanSellWithoutOptions() {
        return canSellWithoutOptions;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Integer getNumberOfImages() {
        return numberOfImages;
    }

    @Override
    public Product setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Product setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
        return this;
    }

    @Override
    public Product setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    @Override
    public Product setDescription(I18n description) {
        this.description = description;
        return this;
    }
    @Override
    public Product setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }
    @Override
    public Product setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }
    @Override
    public Product setProductName(I18n productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public Product setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public Product setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    @Override
    public Product setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }
    @Override
    public Product setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
        return this;
    }
    @Override
    public Product setActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }
    @Override
    public Product setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }
    @Override
    public Product setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }
    @Override
    public Product setMetaKeys(String metaKeys) {
        this.metaKeys = metaKeys;
        return this;
    }
    @Override
    public Product setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
        return this;
    }
    @Override
    public Product setAddedBy(User addedBy) {
        this.addedBy = addedBy;
        return this;
    }
    @Override
    public Product setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
        return this;
    }
    @Override
    public Product setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
        return this;
    }
    @Override
    public Product setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    @Override
    public Product setCanSellWithoutOptions(Boolean canSellWithoutOptions) {
        this.canSellWithoutOptions = canSellWithoutOptions;
        return this;
    }

    @Override
    public Product setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Product setNumberOfImages(Integer numberOfImages) {
        this.numberOfImages = numberOfImages;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImpl product = (ProductImpl) o;

        if (getId() != product.getId()) return false;
        if (isUsed() != product.isUsed()) return false;
        if (canSellWithoutOptions != product.canSellWithoutOptions) return false;
        if (!getBrand().equals(product.getBrand())) return false;
        if (!getProductCategory().equals(product.getProductCategory())) return false;
        if (!getProductName().equals(product.getProductName())) return false;
        if (getVendorCode() != null ? !getVendorCode().equals(product.getVendorCode()) : product.getVendorCode() != null)
            return false;
        if (!getColor().equals(product.getColor())) return false;
        return getGender() == product.getGender();
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + getProductCategory().hashCode();
        result = 31 * result + getProductName().hashCode();
        result = 31 * result + (getVendorCode() != null ? getVendorCode().hashCode() : 0);
        result = 31 * result + getColor().hashCode();
        result = 31 * result + (isUsed() ? 1 : 0);
        result = 31 * result + (canSellWithoutOptions ? 1 : 0);
        result = 31 * result + getGender().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductImpl{" +
                "id=" + id +
                ", brand=" + brand +
                ", productCategory=" + productCategory +
                ", productName=" + productName +
                ", vendorCode='" + vendorCode + '\'' +
                ", color=" + color +
                ", description=" + description +
                ", originPrice=" + originPrice +
                ", retailPrice=" + retailPrice +
                ", salePrice=" + salePrice +
                ", currency=" + currency +
                ", isActive=" + isActive +
                ", isUsed=" + isUsed +
                ", importDate=" + importDate +
                ", lastChangeDate=" + lastChangeDate +
                ", addedBy=" + addedBy +
                ", canSellWithoutOptions=" + canSellWithoutOptions +
                ", gender=" + gender +
                ", numberOfImages=" + numberOfImages +
                '}';
    }
}