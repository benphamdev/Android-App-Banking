package com.example.demoapp.LocalDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.demoapp.Models.dao.ExchangeRate;
import com.example.demoapp.Models.dao.ExchangeRateDao;

@Database(entities = {ExchangeRate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExchangeRateDao exchangeRateDao();
}
