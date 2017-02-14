package com.ffwatl.common.currency;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.currency.domain.CurrencyRate;

import java.util.Collections;
import java.util.List;

public final class CurrencyUtil {

    private static List<CurrencyRate> rates = Collections.emptyList();

    public static void setRates(List<CurrencyRate> rates) {
        if(rates != null) {
            CurrencyUtil.rates = rates;
            for(CurrencyRate rate: rates){
                rate.setValue(rate.getValue() * 100);
            }
        }
    }

    public static int getAmountInAnotherCurrency(int amount, Currency from, Currency to){
        CurrencyRate fromRate;
        CurrencyRate toRate;

        if(from == to){
            return amount; // we have equal currencies - nothing to do.
        }

        toRate = getCurrencyRate(to);

        if(from == Currency.UAH){
            // just convert UAH to another currency using rates list;
            amount /= toRate.getValue().intValue();
        }else{
            // if 'from' currency is not equal Currency.UAH we need to make double conversion;
            fromRate = getCurrencyRate(from);

            amount *= fromRate.getValue().intValue();
            amount /= toRate.getValue().intValue();
        }
        return amount;
    }

    /**
     * Returns {@link CurrencyRate} by given {@link Currency}.
     * Throws IllegalArgumentException if there is no such {@link CurrencyRate} in the list;
     * @return {@link CurrencyRate} by given {@link Currency}.
     */

    public static CurrencyRate getCurrencyRate(Currency currency){
        for (CurrencyRate rate: rates){
            if(rate.getCurrency() == currency) return rate;
        }
        throw new IllegalArgumentException("No such currency rate is available: " + currency);
    }


    public static List<CurrencyRate> getRates() {
        return rates;
    }
}