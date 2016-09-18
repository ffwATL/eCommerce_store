package com.ffwatl.manage.entities.items;

import com.ffwatl.manage.entities.items.color.Color;
import com.ffwatl.manage.entities.currency.Currency;
import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.users.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ffw_ATL
 */
@Entity
@Table(name = "items")
@Inheritance( strategy = InheritanceType.JOINED )
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private ItemGroup itemGroup;

    @Column(name = "name", nullable = false)
    private String itemName;

    private String vendorCode;

    @Column(nullable = false, length = 3)
    private int quantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Color color;

    @Column(length = 2)
    private int discount = 0;

    @Column(length = 2048)
    private String description;

    @Column(length = 2048)
    private String extraNotes;

    @Column(length = 1024)
    private String metaInfo;

    private String metaKeys;

    private int originPrice;

    private int salePrice;

    private Currency currency;

    private boolean isActive;

    private boolean isUsed;

    @Column(nullable = false)
    private Date importDate;

    @Column(nullable = false)
    private Timestamp lastChangeDate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User addedBy;

    public User getAddedBy() {
        return addedBy;
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

    public String getDescription() {
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

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public String getItemName() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public void setMetaKeys(String metaKeys) {
        this.metaKeys = metaKeys;
    }

    public void setLastChangeDate(Timestamp lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemGroup=" + itemGroup.getGroupName() +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", color='" + color + '\'' +
                ", discount=" + discount +
                ", originPrice=" + originPrice +
                ", salePrice=" + salePrice +
                ", isActive=" + isActive +
                ", importDate=" + importDate +
                ", lastChangeDate=" + lastChangeDate +
                ", currency=" + currency +
                '}';
    }
}