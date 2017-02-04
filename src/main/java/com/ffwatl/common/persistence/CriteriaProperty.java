package com.ffwatl.common.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;



public interface CriteriaProperty<T, I extends T> {

    CriteriaBuilder getBuilder();

    CriteriaQuery<T> getCriteria();

    Root<I> getRoot();

    CriteriaProperty<T, I> setBuilder(CriteriaBuilder builder);

    CriteriaProperty<T, I> setCriteria(CriteriaQuery<T> criteria);

    CriteriaProperty<T, I> setRoot(Root<I> root);
}