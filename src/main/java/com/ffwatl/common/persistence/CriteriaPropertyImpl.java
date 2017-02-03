package com.ffwatl.common.persistence;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CriteriaPropertyImpl<T, I extends T> implements CriteriaProperty<T,I> {

    private CriteriaBuilder builder;

    private CriteriaQuery<T> criteria;

    private Root<I> root;


    @Override
    public CriteriaBuilder getBuilder() {
        return builder;
    }

    @Override
    public CriteriaQuery<T> getCriteria() {
        return criteria;
    }

    @Override
    public Root<I> getRoot() {
        return root;
    }

    @Override
    public CriteriaPropertyImpl<T, I> setBuilder(CriteriaBuilder builder) {
        this.builder = builder;
        return this;
    }

    @Override
    public CriteriaPropertyImpl<T, I> setCriteria(CriteriaQuery<T> criteria) {
        this.criteria = criteria;
        return this;
    }

    @Override
    public CriteriaPropertyImpl<T, I> setRoot(Root<I> root) {
        this.root = root;
        return this;
    }
}