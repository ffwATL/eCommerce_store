package com.ffwatl.common.persistence;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface FetchModeOption<T, I extends T> {

    CriteriaProperty<T, I> createOrderCriteriaQueryByFetchMode(final FetchMode fetchMode);

    void addFetch(Root<I> root);

    default CriteriaProperty<T, I> buildCriteriaProperty(CriteriaBuilder cb, final FetchMode fetchMode,
                                                         Class<T> iClazz, Class<I> implClazz){
        CriteriaQuery<T> criteria = cb.createQuery(iClazz);
        Root<I> root = criteria.from(implClazz);

        if(fetchMode == FetchMode.FETCHED){
            addFetch(root);
            // do stuff
        }

        criteria.distinct(true);
        criteria.select(root);
        return new CriteriaPropertyImpl<T, I>()
                .setCriteria(criteria)
                .setBuilder(cb)
                .setRoot(root);
    }
}