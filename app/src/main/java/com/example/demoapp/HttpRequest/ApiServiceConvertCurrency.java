package com.example.demoapp.HttpRequest;

import com.example.demoapp.Models.Dto.Response.CurrencyExchangeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServiceConvertCurrency {
    @GET("query/")
    Call<CurrencyExchangeResponse> getExchangeRate(@Query("function") String function,
                                                   @Query("from_currency") String fromCurrency,
                                                   @Query("to_currency") String toCurrency,
                                                   @Query("apikey") String apiKey);
}
