package com.ffwatl.admin.payment.domain.secure;


import com.ffwatl.common.encryption.EncryptionModule;

import javax.persistence.*;

@Entity
@Table(name = "credit_card_payment")
public class CreditCardPaymentImpl implements CreditCardPayment{

    /**
     * Rather than constructing directly, use {@link SecureOrderPaymentService#create(org.broadleafcommerce.core.payment.service.type.PaymentType)}
     * so that the appropriate {@link EncryptionModule} can be hooked up to this entity
     */
    protected CreditCardPaymentImpl() {
        //do not allow direct instantiation -- must at least be package private for bytecode instrumentation
        //this complies with JPA specification requirements for entity construction
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id;

    @Column(name = "pan", nullable = false)
    private String pan;

    @Column(name = "reference_number", nullable = false)
    private String referenceNumber;

    @Column(name = "expiration_month", nullable = false)
    private Integer expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private Integer expirationYear;

    @Column(name = "name_on_card", nullable = false)
    private String nameOnCard;

    @Transient
    private String cvvCode;

    @Transient
    private EncryptionModule encryptionModule;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getPan() {
        return pan;
    }

    @Override
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    @Override
    public Integer getExpirationYear() {
        return expirationYear;
    }

    @Override
    public String getNameOnCard() {
        return nameOnCard;
    }

    @Override
    public String getCvvCode() {
        return cvvCode;
    }

    @Override
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public EncryptionModule getEncryptionModule() {
        return encryptionModule;
    }

    @Override
    public void setEncryptionModule(EncryptionModule encryptionModule) {
        this.encryptionModule = encryptionModule;
    }

    @Override
    public void setPan(String pan) {
        this.pan = pan;
    }

    @Override
    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Override
    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    @Override
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    @Override
    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCardPaymentImpl that = (CreditCardPaymentImpl) o;

        if (getPan() != null ? !getPan().equals(that.getPan()) : that.getPan() != null) return false;
        if (getReferenceNumber() != null ? !getReferenceNumber().equals(that.getReferenceNumber()) : that.getReferenceNumber() != null)
            return false;
        if (getExpirationMonth() != null ? !getExpirationMonth().equals(that.getExpirationMonth()) : that.getExpirationMonth() != null)
            return false;
        return !(getExpirationYear() != null ? !getExpirationYear().equals(that.getExpirationYear()) : that.getExpirationYear() != null);

    }

    @Override
    public int hashCode() {
        int result = getPan() != null ? getPan().hashCode() : 0;
        result = 31 * result + (getReferenceNumber() != null ? getReferenceNumber().hashCode() : 0);
        result = 31 * result + (getExpirationMonth() != null ? getExpirationMonth().hashCode() : 0);
        result = 31 * result + (getExpirationYear() != null ? getExpirationYear().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CreditCardPaymentImpl{" +
                "id=" + id +
                ", pan='" + pan + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", expirationMonth=" + expirationMonth +
                ", expirationYear=" + expirationYear +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", cvvCode='" + cvvCode + '\'' +
                ", encryptionModule=" + encryptionModule +
                '}';
    }
}