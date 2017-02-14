package com.ffwatl.admin.currency.domain;

import java.util.Date;


public interface CurrencyRate {
    long getId();

    Double getValue();

    Currency getCurrency();

    Date getDate();

    CurrencyRate setId(long id);

    CurrencyRate setValue(Double value);

    CurrencyRate setCurrency(Currency currency);

    CurrencyRate setDate(Date date);
}
