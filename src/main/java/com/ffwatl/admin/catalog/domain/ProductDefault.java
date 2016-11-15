package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
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
    private Category itemGroup;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_1")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_1")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_1"))
    })
    private I18n itemName;

    private String vendorCode;

    @Column(nullable = false, length = 3)
    private int quantity;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ColorImpl.class)
    private Color color;

    @Column(length = 2)
    private int discount = 0;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "locale_en", column = @Column(name = "locale_en_2")),
            @AttributeOverride(name = "locale_ru", column = @Column(name = "locale_ru_2")),
            @AttributeOverride(name = "locale_ua", column = @Column(name = "locale_ua_2"))
    })
    private I18n description;

    @Column(length = 2048)
    private String extraNotes;

    @Column(length = 1024)
    private String metaInfo;

    private String metaKeys;

    private int originPrice;

    private int salePrice;

    private Currency currency;

    private Gender gender;

    private boolean isActive;

    private boolean isUsed;

    @Column(nullable = false)
    private Date importDate;

    @Column(nullable = false)
    private Timestamp lastChangeDate;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = UserImpl.class)
    private User addedBy;

    public User getAddedBy() {
        return addedBy;
    }

    public Gender getGender() {
        return gender;
    }

    public String getExtraNotes() {
        return extraNotes;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public I18n getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public String getMetaKeys() {
        return metaKeys;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Category getCategory() {
        return itemGroup;
    }

    public I18n getProductName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public Color getColor() {
        return color;
    }

    public int getDiscount() {
        return discount;
    }

    public int getRetailPrice() {
        return originPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getImportDate() {
        return importDate;
    }

    public Timestamp getLastChangeDate() {
        return lastChangeDate;
    }

    public Product setId(long id) {
        this.id = id;
        return this;
    }

    public Product setDescription(I18n description) {
        this.description = description;
        return this;
    }

    public Product setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Product setItemGroup(Category itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public Product setItemName(I18n itemName) {
        this.itemName = itemName;
        return this;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Product setColor(Color color) {
        this.color = color;
        return this;
    }

    public Product setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public Product setRetailPrice(int originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public Product setSalePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public Product setActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Product setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }

    public Product setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }

    public Product setMetaKeys(String metaKeys) {
        this.metaKeys = metaKeys;
        return this;
    }

    public Product setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
        return this;
    }

    public Product setAddedBy(User addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public Product setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
        return this;
    }

    public Product setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
        return this;
    }

    public Product setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public Product setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
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