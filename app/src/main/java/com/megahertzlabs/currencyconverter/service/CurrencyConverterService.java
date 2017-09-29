package com.megahertzlabs.currencyconverter.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Danilo Lemes @ Happe on 29/09/2017.
 */

public interface CurrencyConverterService {

    @GET("latest")
    Call<ResponseBody> getCurrency(@Query("base") String from);

}
