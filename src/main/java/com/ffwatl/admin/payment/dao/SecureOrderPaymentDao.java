package com.ffwatl.admin.payment.dao;

import com.ffwatl.admin.payment.domain.secure.CreditCardPayment;
import com.ffwatl.admin.payment.domain.secure.Referenced;

/**
 * @author ffw_ATL.
 */
public interface SecureOrderPaymentDao {

    CreditCardPayment findCreditCardPayment(String referenceNumber);

    CreditCardPayment createCreditCardPayment();

    void delete(Referenced securePayment);

    Referenced save(Referenced securePaymentInfo);

}