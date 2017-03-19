package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author ffw_ATL
 */
@Entity
@Table(name = "items")
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

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = CategoryImpl.class)
    @JoinColumn(name = "category_id")
    private Category itemGroup;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_1")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_1")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_1"))
    })
    private I18n itemName;

    @Column(name = "vandor_code")
    private String vendorCode;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ColorImpl.class)
    @JoinColumn(name = "color_id")
    private Color color;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_2")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_2")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_2"))
    })
    private I18n description;

    @Column(name = "extra_notes", length = 2048)
    private String extraNotes;

    @Column(name = "meta_info", length = 1024)
    private String metaInfo;

    @Column(name = "meta_keys")
    private String metaKeys;

    @Column(name = "origin_price")
    private int originPrice;

    @Column(name = "retail_price")
    private int retailPrice;

    @Column(name = "sale_price")
    private int salePrice;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "used")
    private boolean isUsed;

    @Column(name = "import_dt", nullable = false)
    private Date importDate;

    @Column(name = "last_change_dt", nullable = false)
    private Timestamp lastChangeDate;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    @JoinColumn(name = "added_by_user_id")
    private User addedBy;

    @Column(name = "can_sell_without_attributes")
    private boolean canSellWithoutOptions = false;

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
    public Gender getGender() {
        return gender;
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
    public boolean isUsed() {
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
    public Category getCategory() {
        return itemGroup;
    }

    @Override
    public I18n getProductName() {
        return itemName;
    }

    @Override
    public int getQuantity() {
        int quantity = 0;
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
    public int getOriginPrice() {
        return originPrice;
    }

    @Override
    public int getRetailPrice() {
        return retailPrice;
    }
    @Override
    public int getSalePrice() {
        return salePrice;
    }
    @Override
    public boolean isActive() {
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
    public Product setItemGroup(Category itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }
    @Override
    public Product setItemName(I18n itemName) {
        this.itemName = itemName;
        return this;
    }

    @Override
    public Product setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public Product setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    @Override
    public Product setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }
    @Override
    public Product setSalePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }
    @Override
    public Product setActive(boolean isActive) {
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
    public Product setIsUsed(boolean isUsed) {
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
    public Product setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImpl product = (ProductImpl) o;

        if (getId() != product.getId()) return false;
        if (getOriginPrice() != product.getOriginPrice()) return false;
        if (getRetailPrice() != product.getRetailPrice()) return false;
        if (getSalePrice() != product.getSalePrice()) return false;
        if (isUsed() != product.isUsed()) return false;
        if (canSellWithoutOptions != product.canSellWithoutOptions) return false;
        if (getProductAttributes() != null ? !getProductAttributes().equals(product.getProductAttributes()) : product.getProductAttributes() != null)
            return false;
        if (!getBrand().equals(product.getBrand())) return false;
        if (!itemGroup.equals(product.itemGroup)) return false;
        if (!itemName.equals(product.itemName)) return false;
        if (getVendorCode() != null ? !getVendorCode().equals(product.getVendorCode()) : product.getVendorCode() != null)
            return false;
        if (getColor() != null ? !getColor().equals(product.getColor()) : product.getColor() != null) return false;
        if (getDescription() != null ? !getDescription().equals(product.getDescription()) : product.getDescription() != null)
            return false;
        if (getExtraNotes() != null ? !getExtraNotes().equals(product.getExtraNotes()) : product.getExtraNotes() != null)
            return false;
        if (getMetaInfo() != null ? !getMetaInfo().equals(product.getMetaInfo()) : product.getMetaInfo() != null)
            return false;
        if (getMetaKeys() != null ? !getMetaKeys().equals(product.getMetaKeys()) : product.getMetaKeys() != null)
            return false;
        if (getCurrency() != product.getCurrency()) return false;
        if (getGender() != product.getGender()) return false;
        if (!getImportDate().equals(product.getImportDate())) return false;
        if (getLastChangeDate() != null ? !getLastChangeDate().equals(product.getLastChangeDate()) : product.getLastChangeDate() != null)
            return false;
        return !(getAddedBy() != null ? !getAddedBy().equals(product.getAddedBy()) : product.getAddedBy() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getProductAttributes() != null ? getProductAttributes().hashCode() : 0);
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + itemGroup.hashCode();
        result = 31 * result + itemName.hashCode();
        result = 31 * result + (getVendorCode() != null ? getVendorCode().hashCode() : 0);
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getExtraNotes() != null ? getExtraNotes().hashCode() : 0);
        result = 31 * result + (getMetaInfo() != null ? getMetaInfo().hashCode() : 0);
        result = 31 * result + (getMetaKeys() != null ? getMetaKeys().hashCode() : 0);
        result = 31 * result + getOriginPrice();
        result = 31 * result + getRetailPrice();
        result = 31 * result + getSalePrice();
        result = 31 * result + getCurrency().hashCode();
        result = 31 * result + getGender().hashCode();
        result = 31 * result + (isUsed() ? 1 : 0);
        result = 31 * result + getImportDate().hashCode();
        result = 31 * result + (getLastChangeDate() != null ? getLastChangeDate().hashCode() : 0);
        result = 31 * result + (getAddedBy() != null ? getAddedBy().hashCode() : 0);
        result = 31 * result + (canSellWithoutOptions ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductImpl{" +
                "id=" + id +
                ", productAttributes=" + productAttributes +
                ", brand=" + brand +
                ", itemGroup=" + itemGroup +
                ", itemName=" + itemName +
                ", vendorCode='" + vendorCode + '\'' +
                ", quantity=" + getQuantity() +
                ", color=" + color +
                ", description=" + description +
                ", extraNotes='" + extraNotes + '\'' +
                ", metaInfo='" + metaInfo + '\'' +
                ", metaKeys='" + metaKeys + '\'' +
                ", originPrice=" + originPrice +
                ", retailPrice=" + retailPrice +
                ", salePrice=" + salePrice +
                ", currency=" + currency +
                ", gender=" + gender +
                ", isActive=" + isActive +
                ", isUsed=" + isUsed +
                ", importDate=" + importDate +
                ", lastChangeDate=" + lastChangeDate +
                ", addedBy=" + addedBy +
                '}';
    }
}