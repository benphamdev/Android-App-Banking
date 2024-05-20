package com.example.demoapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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

public class BalanceAlertFragment extends Fragment {

    private RecyclerView recyclerView;
    private BalanceAlertAdapter adapter;
    private MaterialSearchBar searchBar;
    private ArrayList<String> notificationList = new ArrayList<>();

    private Button btnXuat;
    private ImageButton btnTim;
    private String stk;
    private Calendar calendar;
    private TextView tvNgay1, tvNgay2;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int userId;

    private String ngay1 = "2024-05-15", ngay2 = "2024-05-17";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.fragment_balance_alert, container, false);

        btnXuat = rootView.findViewById(R.id.btn_xuat_file);
        recyclerView = rootView.findViewById(R.id.rcv_notification_balance);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchBar = rootView.findViewById(R.id.searchBarbalance);
        adapter = new BalanceAlertAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        tvNgay1 = rootView.findViewById(R.id.tv_ngay_1);
        tvNgay2 = rootView.findViewById(R.id.tv_ngay_2);
        btnTim = rootView.findViewById(R.id.btn_tim);

        Context context = getActivity();
        SharePreferencesManager manager1 = new SharePreferencesManager(context);
        userId = manager1.getUserId();

        setupSearchBar();
        ApiSTKAccount();
//        ApiFullLSGD();

        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiKSGD();
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
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


        return rootView;
    }

    public void Ngay1() {
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
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
                getContext(),
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
    }

    private void showDepositConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Gửi file thành công");
        builder.setMessage("Quá trình gửi file về email đã hoàn tất thành công.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), MainActivity.class));
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
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "File: " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "File: " + response.errorBody().string());
                    } catch(IOException e) {
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
                if(response.isSuccessful()) {
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


    private void setupSearchBar() {
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // Do nothing
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                // Thực hiện tìm kiếm khi người dùng xác nhận tìm kiếm
                performSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                // Do nothing
            }
        });
    }

    private void performSearch(String query) {
        // Xử lý tìm kiếm và cập nhật RecyclerView
        ArrayList<String> filteredList = new ArrayList<>();
        for (String notification : notificationList) {
            if (notification.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(notification);
            }
        }
        adapter.setFilter(filteredList);
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
