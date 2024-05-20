package com.example.demoapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.demoapp.Models.Dto.dao.ExchangeRate;
import com.example.demoapp.Models.Dto.dao.ExchangeRateDao;

@Database(entities = {ExchangeRate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExchangeRateDao exchangeRateDao();
}
