package com.example.demoapp.Models.Dto.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exchange_rate")
public class ExchangeRate {
private String fromCurrencyCode;
    @PrimaryKey
    @NonNull
    private String fromCurrencyName;
    private String toCurrencyCode;
    private String toCurrencyName;
    private String exchangeRate;
    private String lastRefreshed;
    private String timeZone;
    private String bidPrice;
    private String askPrice;

    public ExchangeRate(
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
