package com.ffwatl.admin.order.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@Repository("order_number_dao")
public class OrderNumberDaoImpl implements OrderNumberDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public String findLastOrderNumber() {
        String result = "0";
        List<String> resultList = em.createQuery("SELECT o.orderNumber FROM OrderImpl o " +
                "WHERE o.submitDate=(SELECT MAX(o2.submitDate) FROM OrderImpl o2)", String.class).getResultList();
        if(resultList != null && resultList.size() > 0){
            result = resultList.get(0);
        }
        return result;
    }

}