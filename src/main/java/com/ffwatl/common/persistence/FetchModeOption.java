package com.ffwatl.common.persistence;


public interface FetchModeOption<T, I extends T> {

    CriteriaProperty<T, I> createOrderCriteriaQueryByFetchMode(final FetchMode fetchMode);
}