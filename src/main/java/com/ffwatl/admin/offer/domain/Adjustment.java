package com.ffwatl.admin.offer.domain;

import java.io.Serializable;


public interface Adjustment extends Serializable {

    long getId();
    Offer getOffer();
    String getReason();
    int getValue();

    void setId(long id);
    void setValue(int value);
    void setReason(String reason);
}