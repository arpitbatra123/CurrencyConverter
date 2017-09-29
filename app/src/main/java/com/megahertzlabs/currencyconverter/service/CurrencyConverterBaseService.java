package com.megahertzlabs.currencyconverter.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Danilo Lemes @ Happe on 29/09/2017.
 */

public abstract class CurrencyConverterBaseService {

    protected Retrofit retrofit;

    public final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .build();

    public CurrencyConverterBaseService() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.fixer.io/")
                .client(okHttpClient)
                .build();
    }
}
