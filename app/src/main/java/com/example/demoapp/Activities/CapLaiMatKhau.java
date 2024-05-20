package com.example.demoapp.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapLaiMatKhau extends AppCompatActivity {
    TextView tvEmail;
    Button btnVerify;
    ImageView backButton;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_lai_mat_khau);
        setUpUI();
        btnVerify.setVisibility(View.GONE);
        process();
        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                btnVerify.setVisibility(View.VISIBLE);
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiForgotPassword();
            }
        });
    }

    private void apiForgotPassword() {
        String email = tvEmail.getText().toString().trim();
        SharePreferencesManager manager = new SharePreferencesManager(CapLaiMatKhau.this);
        manager.saveEmal(email);
        ApiService.apiService.verifyEmail(email).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "Comment : " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "Comment : " + response.errorBody().string());
                    } catch(IOException e) {
                        Log.d("Catch", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable throwable) {
                Log.e("E:", throwable.getMessage());
            }
        });
        startActivity(new Intent(CapLaiMatKhau.this, VerifyOTPActivity.class));
    }

    private void setUpUI() {
        tvEmail = findViewById(R.id.txt_email_quen_mk);
        btnVerify = findViewById(R.id.btn_tiep_tuc_cap_lai_mat_khau);
        backButton = findViewById(R.id.toolbar_back_cap_lai_mk);
        radioButton = findViewById(R.id.checkbox_confirm);
    }

    private void process() {
        Toolbar toolbar = findViewById(R.id.tool_bar_cap_lai_mk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_cap_lai_mk);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        }
        backButton.setImageDrawable(drawable);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CapLaiMatKhau.this, SignIn.class));
            }
        });
    }
}
