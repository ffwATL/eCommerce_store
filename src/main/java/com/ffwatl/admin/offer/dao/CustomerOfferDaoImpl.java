package com.ffwatl.admin.offer.dao;


import com.ffwatl.admin.offer.domain.CustomerOffer;
import com.ffwatl.admin.offer.domain.CustomerOfferImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.FetchMode;
import com.ffwatl.common.persistence.CriteriaProperty;
import com.ffwatl.common.persistence.CriteriaPropertyImpl;
import com.ffwatl.common.persistence.EntityConfiguration;
import com.ffwatl.common.persistence.FetchModeOption;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Repository("customer_offer_dao")
public class CustomerOfferDaoImpl implements CustomerOfferDao, FetchModeOption<CustomerOffer, CustomerOfferImpl>{

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "entity_configuration")
    private EntityConfiguration entityConfiguration;

    @Override
    public CustomerOffer readCustomerOfferById(long id) {
        return em.find(CustomerOfferImpl.class, id);
    }

    @Override
    public List<CustomerOffer> readCustomerOffersByCustomer(User customer) {
        CriteriaProperty<CustomerOffer, CustomerOfferImpl> property = createOrderCriteriaQueryByFetchMode(FetchMode.DEFAULT);

        CriteriaQuery<CustomerOffer> criteria = property.getCriteria();
        Join<CustomerOffer, User> userJoin = property.getRoot().join("customer", JoinType.INNER);

        criteria.where(property.getBuilder().equal(userJoin.get("id"), customer.getId()));
        return em.createQuery(criteria).getResultList();
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
        return (CustomerOffer) entityConfiguration.createEntityInstance(CustomerOffer.class.getName());
    }

    @Override
    public CriteriaProperty<CustomerOffer, CustomerOfferImpl> createOrderCriteriaQueryByFetchMode(FetchMode fetchMode) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<CustomerOffer> criteria = cb.createQuery(CustomerOffer.class);
        Root<CustomerOfferImpl> root = criteria.from(CustomerOfferImpl.class);

        criteria.select(root);
        return new CriteriaPropertyImpl<CustomerOffer, CustomerOfferImpl>()
                .setBuilder(cb)
                .setCriteria(criteria)
                .setRoot(root);
    }
}