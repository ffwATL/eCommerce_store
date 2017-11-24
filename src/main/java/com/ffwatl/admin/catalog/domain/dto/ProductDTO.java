package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author mmed 11/24/17
 */
//FIXME: update createDump
public class ProductDTO implements Product {

    private long id;
    private List<ProductAttribute> productAttributes;
    private Brand brand;
    private ProductCategory productCategory;
    private I18n productName;
    private String vendorCode;
    private Color color;
    private I18n description;
    private String extraNotes;
    private String metaInfo;
    private String metaKeys;
    private Integer originPrice;
    private Integer retailPrice;
    private Integer salePrice;
    private Currency currency;
    private Boolean isActive;
    private Boolean isUsed;
    private Date importDate;
    private Timestamp lastChangeDate;
    private User addedBy;
    private Boolean canSellWithoutOptions;
    private Gender gender;
    private Integer numberOfImages;


    public long getId() {
        return id;
    }

    @Override
    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public Brand getBrand() {
        return brand;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    @Override
    public User getAddedBy() {
        return addedBy;
    }

    public I18n getProductName() {
        return productName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public Color getColor() {
        return color;
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

    public I18n getDescription() {
        return description;
    }

    public String getExtraNotes() {
        return extraNotes;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public String getMetaKeys() {
        return metaKeys;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public Integer getRetailPrice() {
        return retailPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public Currency getCurrency() {
        return currency;
    }

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

    public Boolean isUsed() {
        return isUsed;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public Integer getNumberOfImages() {
        return numberOfImages;
    }


    public Product setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Product setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
        return this;
    }

    public Product setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public Product setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public Product setProductName(I18n productName) {
        this.productName = productName;
        return this;
    }

    public Product setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
        return this;
    }

    @Override
    public Product setCanSellWithoutOptions(Boolean canSellWithoutOptions) {
        this.canSellWithoutOptions = canSellWithoutOptions;
        return this;
    }

    public Product setColor(Color color) {
        this.color = color;
        return this;
    }

    public Product setDescription(I18n description) {
        this.description = description;
        return this;
    }

    public Product setExtraNotes(String extraNotes) {
        this.extraNotes = extraNotes;
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

    public Product setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
        return this;
    }

    public Product setRetailPrice(Integer retailPrice) {
        this.retailPrice = retailPrice;
        return this;
    }

    public Product setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public Product setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Product setActive(Boolean active) {
        isActive = active;
        return this;
    }

    @Override
    public Product setImportDate(Date importDate) {
        this.importDate = importDate;
        return this;
    }

    public Product setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Product setNumberOfImages(Integer numberOfImages) {
        this.numberOfImages = numberOfImages;
        return this;
    }

    public StringBuilder createDump() {
        StringBuilder sb = new StringBuilder("id=").append(id);

        if (productName != null) sb.append(", productName=").append(productName.getLocale_en());
        if (productAttributes != null) sb.append(", productAttributes=").append(productAttributes.toString());
        if (importDate != null) sb.append(", importDate=").append(importDate);
        if (addedBy != null) sb.append(", addedBy=").append(addedBy);
        if (originPrice != null) sb.append(", originPrice=").append(originPrice);
        if (retailPrice != null) sb.append(", retailPrice=").append(retailPrice);
        if (salePrice != null) sb.append(", salePrice=").append(salePrice);

        if (isActive != null) sb.append(", isActive=").append(isActive);
        if (isUsed != null) sb.append(", isUsed=").append(isUsed);
        if (currency != null) sb.append(", currency=").append(currency);

        if (brand != null) sb.append(", brand=").append(brand.toString());
        if (productCategory != null) sb.append(", productCategory=").append(productCategory.getCategoryName().getLocale_en());
        if (vendorCode != null) sb.append(", vendorCode='").append(vendorCode).append('\'');
        if (color != null) sb.append(", color=").append(color.getColorName());
        if (description != null) sb.append(", description=").append(description.getLocale_en());

        if (gender != null) sb.append(", gender=").append(gender).append(" ");
        return sb;
    }

    @Override
    public String toString() {
        return "ProductDTO: { " + createDump().toString() + "}";
    }
}