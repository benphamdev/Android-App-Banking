package com.example.demoapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.Activities.authen.SignIn;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.dto.requests.ChangePasswordRequest;
import com.example.demoapp.Models.dto.response.BaseResponse;
import com.example.demoapp.R;
import com.example.demoapp.Utils.sharePreferences.SharePreferencesManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatLaiMatKhau extends AppCompatActivity {

    EditText tvMKCu, tvMKCuLai;
    Button btnDoiMatKhau;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lai_mat_khau);
        SharePreferencesManager manager = new SharePreferencesManager(DatLaiMatKhau.this);
        email = manager.getEmail();

        setUpUI();
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetPasswordSuccessDialog();
            }
        });

    }

    private void showResetPasswordSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đặt lại mật khẩu thành công");
        builder.setMessage(
                "Mật khẩu của bạn đã được đặt lại thành công. Vui lòng đăng nhập lại với mật khẩu mới.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                resetPassword();
                startActivity(new Intent(DatLaiMatKhau.this, SignIn.class));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetPassword() {
        String password = tvMKCu.getText()
                                .toString();
        String confirmPassword = tvMKCu.getText()
                                       .toString();
        ChangePasswordRequest ChangePasswordRequest = new ChangePasswordRequest(
                password,
                confirmPassword
        );
        ApiService.apiService.resetPassword(email, ChangePasswordRequest)
                             .enqueue(new Callback<BaseResponse<Void>>() {
                                 @Override
                                 public void onResponse(
                                         Call<BaseResponse<Void>> call,
                                         Response<BaseResponse<Void>> response
                                 ) {
                                     if (response.isSuccessful()) {
                                         Log.d(
                                                 "SUCCESS",
                                                 "Comment : " + response.body()
                                                                        .toString()
                                         );
                                         startActivity(new Intent(
                                                 DatLaiMatKhau.this,
                                                 SignIn.class
                                         ));
                                     } else {
                                         try {
                                             Log.d("TAG", "Comment : " + response.errorBody()
                                                                                 .string());

                                         } catch (IOException e) {
                                             Log.d("Catch", e.getMessage());
                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(
                                         Call<BaseResponse<Void>> call, Throwable throwable
                                 ) {
                                     Log.e("E:", throwable.getMessage());
                                 }
                             });
    }

    private void setUpUI() {
        tvMKCu = findViewById(R.id.mat_khau_cu);
        tvMKCuLai = findViewById(R.id.mat_khau_cu_lai);
        btnDoiMatKhau = findViewById(R.id.btn_doi_mat_khau);
    }
}