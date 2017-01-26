package com.ffwatl.admin.offer.dao;


import com.ffwatl.admin.offer.domain.CustomerOffer;
import com.ffwatl.admin.offer.domain.CustomerOfferImpl;
import com.ffwatl.admin.user.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerOfferDaoImpl implements CustomerOfferDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public CustomerOffer readCustomerOfferById(long customerOfferId) {
        return em.find(CustomerOfferImpl.class, customerOfferId);
    }

    @Override
    public List<CustomerOffer> readCustomerOffersByCustomer(User customer) {
        return em.createNamedQuery("read_customer_offers_by_customer_id")
                .setParameter("id", customer.getId())
                .getResultList();
    }

    @Override
    public CustomerOffer save(CustomerOffer customerOffer) {
        return em.merge(customerOffer);
    }

    @Override
    public void delete(CustomerOffer customerOffer) {
        em.remove(customerOffer);
    }

    @Override
    public CustomerOffer create() {
        return null;
    }
}