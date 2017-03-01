package com.ffwatl.admin.payment.domain.secure;


import com.ffwatl.admin.payment.service.SecureOrderPaymentService;

/**
 * Entity associated with sensitive, secured credit card data. This data is stored specifically in the blSecurePU persistence.
 * All fetches and creates should go through {@link SecureOrderPaymentService} in order to properly decrypt/encrypt the data
 * from/to the database.
 * @see {@link Referenced}
 */
public interface CreditCardPayment extends Referenced{

    /**
     * @return the id
     */
    @Override
    long getId();

    /**
     * @return the pan
     */
    String getPan();

    /**
     * @return the expirationMonth
     */
    int getExpirationMonth();

    /**
     * @return the expirationYear
     */
    int getExpirationYear();

    /**
     * @return the nameOnCard
     */
    String getNameOnCard();

    String getCvvCode();


    /**
     * @param id the id to set
     */
    @Override
    void setId(long id);

    /**
     * @param pan the pan to set
     */
    void setPan(String pan);

    /**
     * @param expirationMonth the expirationMonth to set
     */
    void setExpirationMonth(int expirationMonth);

    /**
     * @param expirationYear the expirationYear to set
     */
    void setExpirationYear(int expirationYear);

    /**
     * @param nameOnCard the name on the card to set
     */
    void setNameOnCard(String nameOnCard);

    void setCvvCode(String cvvCode);
}