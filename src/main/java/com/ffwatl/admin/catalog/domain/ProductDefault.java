package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ffw_ATL
 */
@Entity
@Table(name = "items")
@Inheritance( strategy = InheritanceType.JOINED )
public class ProductDefault implements Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Column(name = "quantity", nullable = false, length = 3)
    private int quantity;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ColorImpl.class)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "discount", length = 2)
    private int discount = 0;

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

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = OfferImpl.class)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Override
    public long getId() {
        return id;
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
        return quantity;
    }
    @Override
    public Color getColor() {
        return color;
    }
    @Override
    public int getDiscount() {
        return discount;
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
    public Offer getOffer() {
        return this.offer;
    }
    @Override
    public Product setId(long id) {
        this.id = id;
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
    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
    @Override
    public Product setColor(Color color) {
        this.color = color;
        return this;
    }
    @Override
    public Product setDiscount(int discount) {
        this.discount = discount;
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
    public Product setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDefault that = (ProductDefault) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getDiscount() != that.getDiscount()) return false;
        if (getOriginPrice() != that.getOriginPrice()) return false;
        if (getRetailPrice() != that.getRetailPrice()) return false;
        if (getSalePrice() != that.getSalePrice()) return false;
        if (isActive() != that.isActive()) return false;
        if (isUsed() != that.isUsed()) return false;
        if (itemGroup != null ? !itemGroup.equals(that.itemGroup) : that.itemGroup != null) return false;
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false;
        if (getVendorCode() != null ? !getVendorCode().equals(that.getVendorCode()) : that.getVendorCode() != null)
            return false;
        if (getColor() != null ? !getColor().equals(that.getColor()) : that.getColor() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getExtraNotes() != null ? !getExtraNotes().equals(that.getExtraNotes()) : that.getExtraNotes() != null)
            return false;
        if (getMetaInfo() != null ? !getMetaInfo().equals(that.getMetaInfo()) : that.getMetaInfo() != null)
            return false;
        if (getMetaKeys() != null ? !getMetaKeys().equals(that.getMetaKeys()) : that.getMetaKeys() != null)
            return false;
        if (getCurrency() != that.getCurrency()) return false;
        if (getGender() != that.getGender()) return false;
        if (getImportDate() != null ? !getImportDate().equals(that.getImportDate()) : that.getImportDate() != null)
            return false;
        if (getLastChangeDate() != null ? !getLastChangeDate().equals(that.getLastChangeDate()) : that.getLastChangeDate() != null)
            return false;
        if (getAddedBy() != null ? !getAddedBy().equals(that.getAddedBy()) : that.getAddedBy() != null) return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (itemGroup != null ? itemGroup.hashCode() : 0);
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0);
        result = 31 * result + (getVendorCode() != null ? getVendorCode().hashCode() : 0);
        result = 31 * result + getQuantity();
        result = 31 * result + (getColor() != null ? getColor().hashCode() : 0);
        result = 31 * result + getDiscount();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getExtraNotes() != null ? getExtraNotes().hashCode() : 0);
        result = 31 * result + (getMetaInfo() != null ? getMetaInfo().hashCode() : 0);
        result = 31 * result + (getMetaKeys() != null ? getMetaKeys().hashCode() : 0);
        result = 31 * result + getOriginPrice();
        result = 31 * result + getRetailPrice();
        result = 31 * result + getSalePrice();
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + (isUsed() ? 1 : 0);
        result = 31 * result + (getImportDate() != null ? getImportDate().hashCode() : 0);
        result = 31 * result + (getLastChangeDate() != null ? getLastChangeDate().hashCode() : 0);
        result = 31 * result + (getAddedBy() != null ? getAddedBy().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDefault{" +
                "id=" + id +
                ", itemGroup=" + itemGroup +
                ", itemName=" + itemName +
                ", vendorCode='" + vendorCode + '\'' +
                ", quantity=" + quantity +
                ", color=" + color +
                ", discount=" + discount +
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
                ", offer=" + offer +
                '}';
    }
}