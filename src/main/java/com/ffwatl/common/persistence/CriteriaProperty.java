package com.ffwatl.common.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;



public interface CriteriaProperty<T, I extends T> {

    CriteriaBuilder getBuilder();

    CriteriaQuery<T> getCriteria();

    Root<I> getRoot();

    CriteriaPropertyImpl setBuilder(CriteriaBuilder builder);

    CriteriaPropertyImpl setCriteria(CriteriaQuery<T> criteria);

    CriteriaPropertyImpl setRoot(Root<I> root);
}