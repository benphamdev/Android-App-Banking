package com.example.demoapp.Activities.admin.saving;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Activities.admin.user.UserActivity;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDisbursement;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingActivity extends AppCompatActivity {
    private RecyclerView rcvSaving;
    private SavingAdapter savingAdapter;
    private Saving savingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);
        rcvSaving = findViewById(R.id.rcv_saving);
        rcvSaving.setLayoutManager(new LinearLayoutManager(this));
        toolbars();
        loadSavingsData();
    }
    private void toolbars(){
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_saving);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_saving);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(this, R.color.white)
            );
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SavingActivity.this, AdminsActivity.class));
            }
        });
    }
    private void loadSavingsData() {
        SharePreferencesManager sharedPreferences = new SharePreferencesManager(SavingActivity.this);
        int id = sharedPreferences.getUserId();
        ApiService.apiService.getSaving(id).enqueue(new Callback<BaseResponse<Saving>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Saving>> call, @NonNull Response<BaseResponse<Saving>> response) {
                if (response.isSuccessful()) {
                     savingList = response.body().getData();
                        displaySavings(savingList);
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.e("TAG", errorBodyString);
                    } catch (IOException e) {
                        Log.e("TAG", "Error parsing error response: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<Saving>> call, @NonNull Throwable t) {
                Log.e("E", t.getMessage());
            }
        });
    }

    private void displaySavings(Saving savings) {
        savingAdapter = new SavingAdapter(savings);
        rcvSaving.setAdapter(savingAdapter);

    }
}