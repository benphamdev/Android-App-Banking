package com.example.demoapp.Models.dto.response;

import com.google.gson.annotations.SerializedName;

public class CurrencyExchangeResponse {
    @SerializedName("Realtime Currency Exchange Rate")
    private RealtimeCurrencyExchangeRate realtimeCurrencyExchangeRate;

    // getter and setter

    public CurrencyExchangeResponse(RealtimeCurrencyExchangeRate realtimeCurrencyExchangeRate) {
        this.realtimeCurrencyExchangeRate = realtimeCurrencyExchangeRate;
    }

    public RealtimeCurrencyExchangeRate getRealtimeCurrencyExchangeRate() {
        return realtimeCurrencyExchangeRate;
    }

    public void setRealtimeCurrencyExchangeRate(
            RealtimeCurrencyExchangeRate realtimeCurrencyExchangeRate
    ) {
        this.realtimeCurrencyExchangeRate = realtimeCurrencyExchangeRate;
    }
}