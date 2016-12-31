package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.currency.Currency;
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
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = OfferImpl.class)
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
    public int getRetailPrice() {
        return originPrice;
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
    public Product setRetailPrice(int originPrice) {
        this.originPrice = originPrice;
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