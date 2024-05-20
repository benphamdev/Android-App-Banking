package com.example.demoapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity {

    EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;
    Button verifyButton;
    TextView textMobile, textResendOTP;
    String email;
    TextView resendOtpTextView;
    Long timeoutSeconds = 70L;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        setUpUI();
        startResendTimer();
        setUpOTPInput();
        SharePreferencesManager manager = new SharePreferencesManager(VerifyOTPActivity.this);
        email = manager.getEmail();
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processVerifyOTP();
            }
        });

        textResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processResendOPT();
                startResendTimer1();
            }
        });

    }

    private void startResendTimer1() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText("Resend OTP in "+ timeoutSeconds +" seconds");
                if(timeoutSeconds<=0){
                    timeoutSeconds =60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendOtpTextView.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }

    private void startResendTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resendOtpTextView.setText("Resend OTP in "+ timeoutSeconds +" seconds");
                if(timeoutSeconds<=0){
                    timeoutSeconds =60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resendOtpTextView.setEnabled(true);
                    });
                }
            }
        },0,1000);
    }


    private void setInProgress(boolean inProgress) {
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            verifyButton.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            verifyButton.setVisibility(View.VISIBLE);
        }
    }

    private void processResendOPT() {
        ApiService.apiService.verifyEmail(email).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "Comment : " + response.body().toString());
                    startActivity(new Intent(VerifyOTPActivity.this, DatLaiMatKhau.class));
                    setInProgress(false);
                } else {
                    try {
                        Log.d("TAG", "Comment : " + response.errorBody()
                                .string());

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
    }

    private void processVerifyOTP() {
        String mobile = inputCode1.getText().toString() + inputCode2.getText().toString()
                + inputCode3.getText().toString() + inputCode4.getText().toString()
                + inputCode5.getText().toString() + inputCode6.getText().toString();
        int codeOTP = Integer.parseInt(mobile);
        Log.d("VerifyOTP", mobile + " " + email + " " + codeOTP);

        ApiService.apiService.verifyOTP(codeOTP, email).enqueue(new Callback<BaseResponse<Void>>() {
            @Override
            public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "Comment : " + response.body().toString());
                    startActivity(new Intent(VerifyOTPActivity.this, DatLaiMatKhau.class));
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
                Log.e("Error:", throwable.getMessage());
            }
        });
    }

    private void setUpOTPInput() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpUI() {
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 =  findViewById(R.id.inputCode2);
        inputCode3 =  findViewById(R.id.inputCode3);
        inputCode4 =  findViewById(R.id.inputCode4);
        inputCode5 =  findViewById(R.id.inputCode5);
        inputCode6 =  findViewById(R.id.inputCode6);
        textMobile =  findViewById(R.id.textMobile);
        textResendOTP =  findViewById(R.id.tv_resend_OTP);
        resendOtpTextView =  findViewById(R.id.resend_otp_textview);
        verifyButton =  findViewById(R.id.verifyButton);
    }
}