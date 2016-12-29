package com.ffwatl.common.rule;

/**
 * Created by ffw_ATL on 29-Dec-16.
 */
public interface Rule<T,B> {

    long getId();
    boolean isMatch(T target);
    B getBound();

    Rule<T, B> setBound(B bound);
    Rule<T, B> setId(long id);
}