package com.ffwatl.admin.payment.service;

import com.ffwatl.admin.payment.PaymentType;
import com.ffwatl.admin.payment.domain.secure.Referenced;

/**
 * @author ffw_ATL.
 */
public interface SecureOrderPaymentService {

    Referenced findSecurePaymentInfo(String referenceNumber, PaymentType paymentType)/* throws WorkflowException*/;

    Referenced save(Referenced securePayment);

    Referenced create(PaymentType paymentType);

    void remove(Referenced securePayment);

    void findAndRemoveSecurePaymentInfo(String referenceNumber, PaymentType paymentType) /*throws WorkflowException*/;
}