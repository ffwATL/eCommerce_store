package com.ffwatl.manage.entities.promo;

import com.ffwatl.manage.entities.group.ItemGroup;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promo_codes")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private Date validTo;

    private boolean active;

    @Column(length = 2)
    private int discount = 0;

    private boolean validOnSale;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ItemGroup> validOnGroup;

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Date getValidTo() {
        return validTo;
    }

    public boolean isActive() {
        return active;
    }

    public int getDiscount() {
        return discount;
    }

    public boolean isValidOnSale() {
        return validOnSale;
    }

    public List<ItemGroup> getValidOnGroup() {
        return validOnGroup;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setValidOnSale(boolean validOnSale) {
        this.validOnSale = validOnSale;
    }

    public void setValidOnGroup(List<ItemGroup> validOnGroup) {
        this.validOnGroup = validOnGroup;
    }

    @Override
    public String toString() {
        return "PromoCode{" +
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