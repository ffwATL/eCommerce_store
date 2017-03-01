package com.ffwatl.admin.payment.service;

import com.ffwatl.admin.payment.PaymentType;
import com.ffwatl.admin.payment.dao.SecureOrderPaymentDao;
import com.ffwatl.admin.payment.domain.secure.CreditCardPayment;
import com.ffwatl.admin.payment.domain.secure.Referenced;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("secure_order_payment_service")
@Transactional(readOnly = true)
public class SecureOrderPaymentServiceImpl implements SecureOrderPaymentService {

    @Resource(name = "secure_order_payment_dao")
    private SecureOrderPaymentDao secureOrderPaymentDao;

    @Override
    public Referenced findSecurePaymentInfo(String referenceNumber, PaymentType paymentType) {
        Referenced referenced = null;

        if (paymentType == null) throw new IllegalArgumentException("actual PaymentType can't be null!");
        else if (paymentType == PaymentType.CREDIT_CARD_PAYMENT){
            referenced = findCreditCardPaymentByReferenceNumber(referenceNumber);
        }
        return referenced;
    }

    @Override
    public Referenced save(Referenced securePayment) {
        if(securePayment == null) throw new IllegalArgumentException("SecurePayment can't be null!");
        return secureOrderPaymentDao.save(securePayment);
    }

    @Override
    public Referenced create(PaymentType paymentType) {
        if (paymentType == null) throw new IllegalArgumentException("Can't create Referenced object by given PaymentType. Value = null");

        Referenced referenced = null;
        if (paymentType == PaymentType.CREDIT_CARD_PAYMENT){
            referenced = secureOrderPaymentDao.createCreditCardPayment();
        }
        return referenced;
    }

    @Override
    public void remove(Referenced securePayment) {
        if (securePayment == null) throw new IllegalArgumentException("SecurePayment can't be null! Nothing to delete.");
        secureOrderPaymentDao.delete(securePayment);
    }

    @Override
    @Transactional
    public void findAndRemoveSecurePaymentInfo(String referenceNumber, PaymentType paymentType) {
        Referenced referenced = findSecurePaymentInfo(referenceNumber, paymentType);

        if(referenced != null){
            remove(referenced);
        }
    }

    private CreditCardPayment findCreditCardPaymentByReferenceNumber(String referenceNumber){
        return secureOrderPaymentDao.findCreditCardPayment(referenceNumber);
    }
}
