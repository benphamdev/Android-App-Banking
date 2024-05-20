package com.example.demoapp.Utils.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.demoapp.HttpRequest.ApiServiceConvertCurrency;
import com.example.demoapp.HttpRequest.RetrofitClientCV;
import com.example.demoapp.Models.Dto.Response.CurrencyExchangeResponse;
import com.example.demoapp.Models.Dto.Response.RealtimeCurrencyExchangeRate;
import com.example.demoapp.Models.Dto.dao.ExchangeRate;
import com.example.demoapp.database.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateExchangeRateWorker extends Worker {
    //    String [] fromCurrencies = {"USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD", "SGD", "HKD", "KRW", "THB"};

    String [] fromCurrencies = {"JPY", "GBP"};
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    AppDatabase appDatabase;

    public UpdateExchangeRateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Fetch the exchange rate and update the database
        // This is similar to the code in your MainActivity
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exchange_rate_db").build();

        RetrofitClientCV retrofitClient = new RetrofitClientCV();
        ApiServiceConvertCurrency apiService = retrofitClient.getRetrofit().create(ApiServiceConvertCurrency.class);
        for (String fromCurrency : fromCurrencies) {
            apiService.getExchangeRate("CURRENCY_EXCHANGE_RATE", fromCurrency, "VND", "P4T3K99FVT6ZG7KT")
                      .enqueue(new Callback<CurrencyExchangeResponse>() {
                          @Override
                          public void onResponse(
                                  Call<CurrencyExchangeResponse> call, @NonNull Response<CurrencyExchangeResponse> response) {
                              if (response.isSuccessful()) {
                                  CurrencyExchangeResponse currencyExchangeResponse = response.body();
                                  RealtimeCurrencyExchangeRate realtimeCurrencyExchangeRate = currencyExchangeResponse.getRealtimeCurrencyExchangeRate();
                                  Log.d("UpdateExchangeRateWorker", realtimeCurrencyExchangeRate.getFromCurrencyCode() + "onResponse: " + realtimeCurrencyExchangeRate.getExchangeRate());

                                  executorService.execute(new Runnable() {
                                      @Override
                                      public void run() {
                                          appDatabase.exchangeRateDao().insert(new ExchangeRate(
                                                  realtimeCurrencyExchangeRate.getFromCurrencyCode(),
                                                  realtimeCurrencyExchangeRate.getFromCurrencyName(),
                                                  realtimeCurrencyExchangeRate.getToCurrencyCode(),
                                                  realtimeCurrencyExchangeRate.getToCurrencyName(),
                                                  realtimeCurrencyExchangeRate.getExchangeRate(),
                                                  realtimeCurrencyExchangeRate.getLastRefreshed(),
                                                  realtimeCurrencyExchangeRate.getTimeZone(),
                                                  realtimeCurrencyExchangeRate.getBidPrice(),
                                                  realtimeCurrencyExchangeRate.getAskPrice()
                                          ));
                                      }
                                  });

                                  Log.d("UpdateExchangeRateWorker", "onResponse: Exchange rate saved to the database" );
                              } else {
                                  Log.d("UpdateExchangeRateWorker", "onResponse: " + response.message());
                              }
                          }

                          @Override
                          public void onFailure(Call<CurrencyExchangeResponse> call, Throwable t) {
                              Log.d("UpdateExchangeRateWorker", "onFailure: " + t.getMessage());
                          }
                      });
        }

        return Result.success();
    }
}