package com.ffwatl.admin.payment.dao;

import com.ffwatl.admin.payment.domain.secure.CreditCardPayment;
import com.ffwatl.admin.payment.domain.secure.CreditCardPaymentImpl;
import com.ffwatl.admin.payment.domain.secure.Referenced;
import com.ffwatl.common.persistence.EntityConfiguration;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@Repository("secure_order_payment_dao")
public class SecureOrderPaymentDaoImpl implements SecureOrderPaymentDao {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;


    @Override
    public CreditCardPayment findCreditCardPayment(String referenceNumber) {
        List<CreditCardPaymentImpl> list = em.createQuery("SELECT c FROM CreditCardPaymentImpl c " +
                        "WHERE c.referenceNumber=:referenceNumber",  CreditCardPaymentImpl.class)
                .setParameter("referenceNumber", referenceNumber)
                .getResultList();

        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    public CreditCardPayment createCreditCardPayment() {
        return entityConfiguration.createEntityInstance(CreditCardPayment.class);
    }

    @Override
    public void delete(Referenced securePayment) {
        if(!em.contains(securePayment)){
            securePayment = em.find(securePayment.getClass(), securePayment.getId());
        }
        em.remove(securePayment);
    }

    @Override
    public Referenced save(Referenced securePaymentInfo) {
        return em.merge(securePaymentInfo);
    }
}