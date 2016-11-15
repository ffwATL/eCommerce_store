package com.ffwatl.admin.offer;

import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CategoryImpl;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promo_codes")
public class PromoCodeImpl implements PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private Date validTo;

    private boolean active;

    @Column(length = 2)
    private int discount = 0;

    private boolean validOnSale;

    @OneToMany(cascade = CascadeType.PERSIST, targetEntity = CategoryImpl.class)
    private List<Category> validOnGroup;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Date getValidTo() {
        return validTo;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public int getDiscount() {
        return discount;
    }

    @Override
    public boolean isValidOnSale() {
        return validOnSale;
    }

    @Override
    public List<Category> getValidOnGroup() {
        return validOnGroup;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public void setValidOnSale(boolean validOnSale) {
        this.validOnSale = validOnSale;
    }

    @Override
    public void setValidOnGroup(List<Category> validOnGroup) {
        this.validOnGroup = validOnGroup;
    }

    @Override
    public String toString() {
        return "PromoCodeImpl{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", validTo=" + validTo +
                ", active=" + active +
                ", discount=" + discount +
                ", validOnSale=" + validOnSale +
                ", validOnGroup=" + validOnGroup +
                '}';
    }
}