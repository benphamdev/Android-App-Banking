package com.example.demoapp.Models.dto.response;

import com.google.gson.annotations.SerializedName;

public class RealtimeCurrencyExchangeRate {
    @SerializedName("1. From_Currency Code")
    private final String fromCurrencyCode;

    @SerializedName("2. From_Currency Name")
    private final String fromCurrencyName;

    @SerializedName("3. To_Currency Code")
    private final String toCurrencyCode;

    @SerializedName("4. To_Currency Name")
    private final String toCurrencyName;

    @SerializedName("5. Exchange Rate")
    private final String exchangeRate;

    @SerializedName("6. Last Refreshed")
    private final String lastRefreshed;

    @SerializedName("7. Time Zone")
    private final String timeZone;

    @SerializedName("8. Bid Price")
    private final String bidPrice;

    @SerializedName("9. Ask Price")
    private final String askPrice;

    public RealtimeCurrencyExchangeRate(
            String fromCurrencyCode, String fromCurrencyName, String toCurrencyCode,
            String toCurrencyName, String exchangeRate, String lastRefreshed, String timeZone,
            String bidPrice, String askPrice
    ) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.fromCurrencyName = fromCurrencyName;
        this.toCurrencyCode = toCurrencyCode;
        this.toCurrencyName = toCurrencyName;
        this.exchangeRate = exchangeRate;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public String getFromCurrencyName() {
        return fromCurrencyName;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public String getToCurrencyName() {
        return toCurrencyName;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public String getAskPrice() {
        return askPrice;
    }
}
