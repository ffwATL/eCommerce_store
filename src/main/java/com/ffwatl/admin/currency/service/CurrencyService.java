package com.ffwatl.admin.currency.service;

import java.time.LocalDate;



public interface CurrencyService {

    LocalDate getLastUpdate();

    void updateCurrencyRatesToUAH();
}