package com.example.demoapp.Activities.TietKiem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Fragments.HomeFragment;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TatToanTietKiem extends AppCompatActivity {

    TextView tvId, tvStk;
    Button btnXacNhan;
    ImageView backButton;
    Toolbar toolbar;
    private int userId;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tat_toan_tiet_kiem);
        setUpUI();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
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
                    startActivity(new Intent(TatToanTietKiem.this, HomeFragment.class));
                }
            });
        }
        SharePreferencesManager manager = new SharePreferencesManager(this);
        userId = manager.getUserId();
        ApiIDSaving();
        ApiSTKAccount();

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettlementConfirmationDialog();
            }
        });
    }

    private void ApiSTKAccount() {
        ApiService.apiService.getAccountByUserId(userId).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "ACCOUNT: " + response.body().toString());
                    tvStk.setText(response.body().getData().getAccountNumber());
                } else {
                    try {
                        Log.d("TAG", "onResponse: " + response.errorBody()
                                                              .string());

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AccountInfoResponse>> call, Throwable throwable) {
                Log.e("E:", throwable.getMessage());
            }
        });
    }

    private void ApiIDSaving() {
        ApiService.apiService.getSaving(userId).enqueue(new Callback<BaseResponse<Saving>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<Saving>> call, @NonNull Response<BaseResponse<Saving>> response) {
                if (response.isSuccessful()) {
                    Log.d("SUCCESS", "ID_SAVING" + response.body().toString());
                    Saving savingList = response.body().getData();
                    id = savingList.getId();
                    tvId.setText(String.valueOf(id));
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
    }

    private void callApi() {
        ApiService.apiService.cancelSaving(id).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "onResponse: " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "onResponse: " + response.errorBody()
                                                              .string());

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

            }
        });
    }

    private void showSettlementConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận tất toán");
        builder.setMessage("Bạn có chắc chắn muốn tất toán tài khoản tiết kiệm này không?");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callApi();
                showSettlementSuccessDialog();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hủy thực hiện tất toán
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSettlementSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tất toán thành công");
        builder.setMessage("Quá trình tất toán tài khoản tiết kiệm đã hoàn tất thành công.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(TatToanTietKiem.this, MainActivity.class));
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void setUpUI() {
        backButton = findViewById(R.id.toolbar_back_tat_toan_tiet_kiem);
        toolbar = findViewById(R.id.tool_bar_tat_toan_tiet_kiem);
        tvId = findViewById(R.id.tv_id_tiet_kiem);
        tvStk = findViewById(R.id.tv_tk_tk);
        btnXacNhan = findViewById(R.id.btn_xac_nhan);
    }
}