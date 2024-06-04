package com.example.demoapp.Activities.TienIch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Adapters.BalanceAlertAdapter;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserTransaction;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LapYeuCauTraSoat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BalanceAlertAdapter adapter;
    private MaterialSearchBar searchBar;
    private ArrayList<String> notificationList = new ArrayList<>();

    private Button btnTraCuu;
    private ImageButton btnTim;
    private String stk;
    private Calendar calendar;
    private EditText tvNgay1, tvNgay2;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int userId;

    private String ngay1 = "2024-05-15", ngay2 = "2024-05-17";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_yeu_cau_tra_soat);
        Toolbar toolbar = findViewById(R.id.tool_bar_lap_yeu_tra_soat);
        setUI();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_lap_yeu_cau_tra_soat);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LapYeuCauTraSoat.this, MainActivity.class));
            }
        });
        SharePreferencesManager manager1 = new SharePreferencesManager(this);
        userId = manager1.getUserId();

        ApiSTKAccount();
        btnTraCuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDepositConfirmationDialog();
            }
        });

        tvNgay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ngay1();
            }
        });

        tvNgay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ngay2();
            }
        });


    }

    private void setUI() {
        tvNgay1 = findViewById(R.id.app_ngay1);
        tvNgay2 = findViewById(R.id.app_ngay2);
        recyclerView = findViewById(R.id.rcv_notification_balance);
        btnTraCuu = findViewById(R.id.btn_tra_cuu_lap_yeu_tra_soat);
    }

    public void Ngay1() {
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(
                LapYeuCauTraSoat.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(
                            DatePicker view, int year, int month, int dayOfMonth
                    ) {
                        calendar.set(year, month, dayOfMonth);
                        tvNgay1.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },
                year,
                month,
                date
        );
        dialog.show();
    }

    public void Ngay2() {
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(
                LapYeuCauTraSoat.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(
                            DatePicker view, int year, int month, int dayOfMonth
                    ) {
                        calendar.set(year, month, dayOfMonth);
                        tvNgay2.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },
                year,
                month,
                date
        );
        dialog.show();
        ngay1 = tvNgay1.getText().toString();
        ngay2 = tvNgay2.getText().toString();
        ApiService.apiService.getTransactionBetweenStartDateAndEndDate(stk, ngay1, ngay2)
                .enqueue(new Callback<BaseResponse<List<UserTransaction>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<UserTransaction>>> call, Response<BaseResponse<List<UserTransaction>>> response) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    response.message()
                                            .toString()
                            );
                            List<UserTransaction> userTransactions = response.body()
                                    .getData();
                            for (UserTransaction user : userTransactions) {
                                notificationList.add(convertUserTransactionToString(
                                        user));
                                Log.d("okeeeeee", user.getToAccount());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            try {
                                Log.e(
                                        "ok",
                                        response.errorBody()
                                                .toString()
                                );
                            } catch (Exception e) {
                                Log.e("E", e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<UserTransaction>>> call, Throwable throwable) {
                        Log.e("E:", throwable.getMessage());
                    }
                });
    }

    private void showDepositConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LapYeuCauTraSoat.this);
        builder.setTitle("Xác nhận gửi file về email");
        builder.setMessage("Bạn có chắc chắn muốn gửi file về email không?");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ApiFile();
                showDepositSuccessDialog();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hủy thực hiện gửi tiết kiệm
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDepositSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LapYeuCauTraSoat.this);
        builder.setTitle("Gửi file thành công");
        builder.setMessage("Quá trình gửi file về email đã hoàn tất thành công.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(LapYeuCauTraSoat.this, MainActivity.class));
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ApiFullLSGD() {
        ApiService.apiService.getTransactionWithAccountID(userId).enqueue(new Callback<BaseResponse<List<UserTransaction>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<UserTransaction>>> call, Response<BaseResponse<List<UserTransaction>>> response) {
                if (response.isSuccessful()) {
                    Log.d(
                            "SUCCESS",
                            response.message()
                                    .toString()
                    );
                    List<UserTransaction> userTransactions = response.body()
                            .getData();
                    for (UserTransaction user : userTransactions) {
                        notificationList.add(convertUserTransactionToString(
                                user));
                        Log.d("okeeeeee", user.getToAccount());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    try {
                        Log.e(
                                "okeeeee1",
                                response.errorBody()
                                        .toString()
                        );
                    } catch (Exception e) {
                        Log.e("Eeeee", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<UserTransaction>>> call, Throwable throwable) {
                Log.e("E:", throwable.getMessage());
            }
        });
    }

    private void ApiFile() {
        ngay1 = tvNgay1.getText().toString();
        ngay2 = tvNgay2.getText().toString();
        ApiService.apiService.exportTransactionBetweenStartDateAndEndDate(stk, ngay1, ngay2).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("SUCCESS", "File: " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "File: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable throwable) {
                Log.e("E:", throwable.getMessage());
            }
        });
    }

    private void ApiKSGD() {
        ngay1 = tvNgay1.getText().toString();
        ngay2 = tvNgay2.getText().toString();
        ApiService.apiService.getTransactionBetweenStartDateAndEndDate(stk, ngay1, ngay2)
                .enqueue(new Callback<BaseResponse<List<UserTransaction>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<UserTransaction>>> call, Response<BaseResponse<List<UserTransaction>>> response) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    response.message()
                                            .toString()
                            );
                            List<UserTransaction> userTransactions = response.body()
                                    .getData();
                            for (UserTransaction user : userTransactions) {
                                notificationList.add(convertUserTransactionToString(
                                        user));
                                Log.d("okeeeeee", user.getToAccount());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            try {
                                Log.e(
                                        "ok",
                                        response.errorBody()
                                                .toString()
                                );
                            } catch (Exception e) {
                                Log.e("E", e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<UserTransaction>>> call, Throwable throwable) {
                        Log.e("E:", throwable.getMessage());
                    }
                });
    }

    private void ApiSTKAccount() {
        ApiService.apiService.getAccountByUserId(userId).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<AccountInfoResponse> baseResponse = response.body();
                    if (baseResponse != null && baseResponse.getData() != null) {
                        AccountInfoResponse accountInfoResponse = baseResponse.getData();
                        String accountNumber = accountInfoResponse.getAccountNumber();
                        Log.d("SUCCESS", "ACCOUNT: " + accountNumber);
                        stk = accountNumber;
                    } else {
                        Log.e("ERROR", "Response body or data is null");
                    }
                } else {
                    try {
                        Log.d("TAG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
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


    private String convertUserTransactionToString(UserTransaction userTransaction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transaction Type: ").append(userTransaction.getTransactionType()).append("\n");
        stringBuilder.append("From Account: ").append(userTransaction.getFromAccount()).append("\n");
        stringBuilder.append("To Account: ").append(userTransaction.getToAccount()).append("\n");
        stringBuilder.append("Amount: ").append(userTransaction.getAmount()).append("\n");
        stringBuilder.append("Status: ").append(userTransaction.getStatus()).append("\n");

        return stringBuilder.toString();
    }
}