package com.ffwatl.admin.currency.service;

import com.ffwatl.admin.currency.domain.CurrencyRate;
import com.ffwatl.common.currency.CurrencyUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Utility methods for common currency operations
 */
@Service("currency_service")
public class CurrencyServiceImpl implements CurrencyService {

    private LocalDate lastUpdate;


    public CurrencyServiceImpl(){
        updateCurrencyRatesToUAH();
    }

    /**
     * Returns the last date when the currency rates were updated;
     * @return the last date when the currency rates were updated;
     */
    @Override
    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Checks if currency rates is up to date and if it not then updating it and the {@link #lastUpdate};
     */
    @Override
    public void updateCurrencyRatesToUAH(){
        LocalDate now = LocalDate.now();
        if(lastUpdate != null && !now.isAfter(lastUpdate)) return;

        try{
            List<CurrencyRate> rates = Collections.emptyList(); // TODO: create and call CurrencyDao to fetch currency rates;
            /*rates = currencyRatesService.findAll();*/
            CurrencyUtil.setRates(rates);
            lastUpdate = now;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}