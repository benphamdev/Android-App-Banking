package com.example.demoapp.Models.Dto.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExchangeRate exchangeRate);

    @Query("SELECT * FROM exchange_rate WHERE fromCurrencyCode = :fromCurrencyCode AND toCurrencyCode = :toCurrencyCode")
    ExchangeRate getExchangeRate(String fromCurrencyCode, String toCurrencyCode);
}
