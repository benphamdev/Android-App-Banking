package com.example.demoapp.Models.Dto.Response;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

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