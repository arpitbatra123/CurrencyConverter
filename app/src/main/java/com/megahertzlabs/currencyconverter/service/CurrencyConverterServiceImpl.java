package com.megahertzlabs.currencyconverter.service;

/**
 * Created by Danilo Lemes @ Happe on 29/09/2017.
 */

public class CurrencyConverterServiceImpl extends CurrencyConverterBaseService {

    private CurrencyConverterService currencyConverterService;

    public CurrencyConverterServiceImpl() {
        super();
        this.currencyConverterService = this.retrofit.create(CurrencyConverterService.class);
    }

    public CurrencyConverterService getCurrencyConverterService() {
        return currencyConverterService;
    }
}
