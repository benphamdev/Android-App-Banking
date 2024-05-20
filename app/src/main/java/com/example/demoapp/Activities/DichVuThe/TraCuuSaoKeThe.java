package com.example.demoapp.Activities.DichVuThe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraCuuSaoKeThe extends AppCompatActivity {
    PieChart pieChart;
    ArrayList<String> labels = new ArrayList<>();
    private String tokenApi;
    private Toolbar toolbar;
    private ImageView backButton;
    private LinearLayout linearLayout;
    private String sodu;
    private String amount = "0.00";
    private String refund = "0.00";
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_cuu_sao_ke_the);

        SharePreferencesManager sharePreferencesManager = new SharePreferencesManager(this);
        tokenApi = sharePreferencesManager.getToken();
        SharePreferencesManager manager = new SharePreferencesManager(this);
        userId = manager.getUserId();

        process();

    }

    private void process() {

        ApiService.apiService.getAccountByUserId(userId).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("SUCCESS", "so du: " + response.body().getData().getAccountBalance());
                    labels.add("Tài khoản thanh toán \n" + response.body().getData().getAccountBalance() + "VND");
                } else {
                    try {
                        Log.d("TAG", "so du: " + response.errorBody()
                                .string());

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AccountInfoResponse>> call, Throwable throwable) {
                Log.d("EEE", throwable.getMessage());
            }
        });

        ApiService.apiService.getSaving(userId).enqueue(new Callback<BaseResponse<Saving>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Saving>> call, @NonNull Response<BaseResponse<Saving>> response) {
                if (response.isSuccessful()) {
                    Log.d("SUCCESS", "ID_SAVING" + response.body().toString());
                    Saving savingList = response.body().getData();
                    amount = String.valueOf(savingList.getBaseAmount());
                    refund = String.valueOf(savingList.getRefundAmount());
                } else {
                    try {
                        String errorBodyString = response.errorBody().string();
                        Log.e("TAGGGGGGGGG", errorBodyString);
                    } catch (IOException e) {
                        Log.e("TAGGGGGGFFFF", "Error parsing error response: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<Saving>> call, @NonNull Throwable t) {
                Log.e("EEEEE", t.getMessage());
            }
        });

        toolbar = findViewById(R.id.tool_bar_tra_cuu_tai_khoan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        backButton = findViewById(R.id.toolbar_back_tra_cuu_tai_khoan);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TraCuuSaoKeThe.this, MainActivity.class));
            }
        });
        linearLayout = findViewById(R.id.main_linear);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        pieChart = findViewById(R.id.pie_chart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        labels.add("Nợ ngân hàng");
        labels.add("Lãi suất gửi tiết kiệm \n 5.5%");
        labels.add("Tài khoản tiết kiệm" + "\n Tiền gửi: " + amount + "\n Tiền trả về: " + refund);

        pieEntries.add(new PieEntry(25f));
        pieEntries.add(new PieEntry(25f));
        pieEntries.add(new PieEntry(25f));
        pieEntries.add(new PieEntry(25f));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Danh sách tài khoản");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(15f);
        pieDataSet.setDrawValues(false);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(50f);
        pieChart.setData(pieData);
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleRadius(10f);
        pieChart.setData(pieData);
        pieChart.getDescription()
                .setEnabled(false);
        pieChart.getLegend()
                .setEnabled(false);
        pieChart.animateXY(4000, 4000);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Lấy chỉ số của phần được chọn
                int index = (int) h.getX();

                // Hiển thị chữ mô tả ở trung tâm của biểu đồ Pie
                pieChart.setCenterText(labels.get(index));

            }

            @Override
            public void onNothingSelected() {
                // Xử lý khi không có phần nào được chọn
            }
        });
    }
}