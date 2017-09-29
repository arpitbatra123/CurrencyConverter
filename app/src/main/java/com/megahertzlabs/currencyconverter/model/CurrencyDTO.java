package com.megahertzlabs.currencyconverter.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Danilo Lemes @ Happe on 29/09/2017.
 */

public class CurrencyDTO implements Serializable {

    private String base;
    private String date;
    private List<Map<String, Double>> rates;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String base, String date, List<Map<String, Double>> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Double>> getRates() {
        return rates;
    }

    public void setRates(List<Map<String, Double>> rates) {
        this.rates = rates;
    }
}
