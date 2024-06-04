package com.example.demoapp.Activities.TienIch;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Models.Dto.dao.ExchangeRate;
import com.example.demoapp.R;
import com.example.demoapp.Utils.worker.UpdateExchangeRateWorker;
import com.example.demoapp.database.AppDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Convert extends AppCompatActivity {

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Double ans;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        Toolbar toolbar = findViewById(R.id.tool_bar_convert);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_convert);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Convert.this, MainActivity.class));
            }
        });
        CountryCodePicker countryCodePicker1 = findViewById(R.id.cpp1);
        CountryCodePicker countryCodePicker2 = findViewById(R.id.cpp2);
        EditText editTextAmountToConvert = findViewById(R.id.edit_text_amount_to_convert);
        EditText editTextAmountToReceive = findViewById(R.id.edit_text_amount_to_receive);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "exchange_rate_db").build();

        // you can change the fromCurrencyCode and toCurrencyCode to any currency code you want
        // for example: fromCurrencyCode = "EUR", toCurrencyCode = "USD"
//        String fromCurrencyCode = "JPY";
//        String toCurrencyCode = "VND";
        // fetch the exchange rate from the database
//        Double exchangeRate = getFromCurrency(fromCurrencyCode, toCurrencyCode);
        final String[] fromCountryCode = {null};
        countryCodePicker1.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                fromCountryCode[0] = countryCodePicker1.getSelectedCountryCode();
                Log.d("hello", fromCountryCode[0]);
            }
        });

        Double exchangeRate = getFromCurrency(fromCountryCode[0], "VND");

        Toast.makeText(this, exchangeRate.toString(), Toast.LENGTH_SHORT)
             .show();

        editTextAmountToConvert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (exchangeRate != null) {
                    String amountToConvertStr = editTextAmountToConvert.getText().toString().trim();
                    if (!amountToConvertStr.isEmpty()) {
                        double amountToConvert = Double.parseDouble(amountToConvertStr);
                        double amountToReceive = amountToConvert * 25448.00000000;
                        editTextAmountToReceive.setText(String.valueOf(amountToReceive));
                    }
                }
            }
        });
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Set the time you want the task to run
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.set(Calendar.HOUR_OF_DAY, 3);
        targetCalendar.set(Calendar.MINUTE, 55);
        targetCalendar.set(Calendar.SECOND, 0);

        // If the current time is past 7:58 AM, schedule the task for next day
        if (calendar.after(targetCalendar)) {
            targetCalendar.add(Calendar.DATE, 1);
        }

        // Calculate the initial delay
        long initialDelay = targetCalendar.getTimeInMillis() - calendar.getTimeInMillis();

        // Convert the initial delay to minutes
        long initialDelayMinutes = TimeUnit.MILLISECONDS.toMinutes(initialDelay);

        // Create the work request
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateExchangeRateWorker.class)
                .setInitialDelay(initialDelayMinutes, TimeUnit.MINUTES)
                .build();

        // Enqueue the work request
        WorkManager.getInstance(this).enqueue(workRequest);

    }
    public Double getFromCurrency(String fromCurrencyCode, String toCurrencyCode) {
        ans = 0.0;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ExchangeRate exchangeRate = appDatabase.exchangeRateDao().getExchangeRate(fromCurrencyCode, toCurrencyCode);
                if(exchangeRate != null){
                    Log.d("getFromCurrency", "Exchange rate: " + exchangeRate.getExchangeRate());
                    ans =  Double.valueOf(exchangeRate.getExchangeRate());
                }else {

                }
            }
        });
        return ans;
    }
}