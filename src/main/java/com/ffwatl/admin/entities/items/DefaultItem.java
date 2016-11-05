package com.ffwatl.admin.entities.items;

import com.ffwatl.admin.entities.currency.Currency;
import com.ffwatl.admin.entities.group.Gender;
import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.color.Color;
import com.ffwatl.admin.entities.users.IUser;
import com.ffwatl.admin.entities.users.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ffw_ATL
 */
@Entity
@Table(name = "items")
@Inheritance( strategy = InheritanceType.JOINED )
public class DefaultItem implements Item{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = ItemGroup.class)
    private IGroup itemGroup;

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

    @ManyToOne(cascade = CascadeType.MERGE)
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

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = User.class)
    private IUser addedBy;

    public IUser getAddedBy() {
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

    public IGroup getItemGroup() {
        return itemGroup;
    }

    public I18n getItemName() {
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

    public int getOriginPrice() {
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

    public Item setId(long id) {
        this.id = id;
        return this;
    }

    public Item setDescription(I18n description) {
        this.description = description;
        return this;
    }

    public Item setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Item setItemGroup(IGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public Item setItemName(I18n itemName) {
        this.itemName = itemName;
        return this;
    }

    public Item setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Item setColor(Color color) {
        this.color = color;
        return this;
    }

    public Item setDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public Item setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public Item setSalePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public Item setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Item setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }

    public Item setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }

    public Item setMetaKeys(String metaKeys) {
        this.metaKeys = metaKeys;
        return this;
    }

    public Item setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
        return this;
    }

    public Item setAddedBy(IUser addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    public Item setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
        return this;
    }

    public Item setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
        return this;
    }

    public Item setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    public Item setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public String toString() {
        return "Item{" +
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