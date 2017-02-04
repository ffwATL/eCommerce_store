package com.ffwatl.common.persistence;

import com.ffwatl.common.FetchMode;


public interface FetchModeOption<T, I extends T> {

    CriteriaProperty<T, I> createOrderCriteriaQueryByFetchMode(final FetchMode fetchMode);
}