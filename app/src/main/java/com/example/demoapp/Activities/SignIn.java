package com.example.demoapp.Activities;

import static com.example.demoapp.Utils.RedirectActivity.redirectActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.LoginRequestDTO;
import com.example.demoapp.Models.Dto.Response.AuthenticationResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import com.example.demoapp.Utils.LoginFormValidationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.protobuf.Api;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    private SharedPreferences preferences;
    private LoginFormValidationHelper loginValidator;
    private TextView txtGoToSignIn;
    private TextInputEditText txt_email, txt_password;
    private TextInputLayout txtEmailLayout, txtPasswordLayout;
    private Button loginBtn;
    private Dialog dialog, dialog2,dialogSuccess2;
    private TextView tvQuenMatKhau;

    private static final String TAG = "SignIn";
    private String token;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_vcb);

        // SET UP UI:
        setUpUI();

        // PROCESS LOGIN:
        processLogin();

        // Gọi hàm để lấy token ở đây
        getToken();

        redirectToRegister();
        redirectToForgotPassword();
    }


    private void getToken() {
        // Gọi hàm lấy token của Firebase Messaging
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // Lấy token thành công
                            token = task.getResult();
                            SharePreferencesManager manager = new SharePreferencesManager(SignIn.this);
                            id = manager.getUserId();
//                            Log.d("tokennnnnn", id + " " + token);
                            SharePreferencesManager manager1 = new SharePreferencesManager(SignIn.this);
                            manager1.saveTokenApp(token);
//                            ApiService.apiService.updatePhoneToken(id, token).enqueue(new Callback<BaseResponse<Void>>() {
//                                @Override
//                                public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
//                                    if(response.isSuccessful()){
//                                        Log.d("Success: ", "token " + response.body().toString());
//                                    }
//                                    else{
//                                        try{
//                                            Log.d("Tag: ", "token " + response.errorBody());
//                                        }
//                                        catch (Exception e){
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<BaseResponse<Void>> call, Throwable throwable) {
//                                    Log.e("EEEE", throwable.getMessage());
//                                }
//                            });
                        } else {
                            // Lấy token thất bại
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        }
                    }
                });
    }

    public void setUpUI() {

        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txtGoToSignIn = findViewById(R.id.txt_go_to_sign_up);
        loginBtn = findViewById(R.id.login_btn);

        txtEmailLayout = findViewById(R.id.txt_email_layout);
        txtPasswordLayout = findViewById(R.id.txt_password_layout);
        tvQuenMatKhau = findViewById(R.id.tv_quen_mat_khau);

        loginValidator = new LoginFormValidationHelper(
                txt_email,
                txt_password,
                txtEmailLayout,
                txtPasswordLayout,
                loginBtn
        );
        txt_email.addTextChangedListener(loginValidator);
        txt_password.addTextChangedListener(loginValidator);
    }
    private void redirectToForgotPassword() {
        tvQuenMatKhau.setOnClickListener(
                v -> redirectActivity(
                        SignIn.this,
                        CapLaiMatKhau.class
                ));
    }

    public void processLogin() {
        loginBtn.setOnClickListener(v -> {
            String email = Objects.requireNonNull(txt_email.getText()).toString();
            String password = Objects.requireNonNull(txt_password.getText()).toString();
            authenticateUser(email, password);
        });
    }

    public void redirectToRegister() {
        txtGoToSignIn.setOnClickListener(
                v -> redirectActivity(
                        SignIn.this,
                        SignUp.class
                ));
    }

    private void authenticateUser(String email, String password) {
        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                email,
                password
        );

        // Method post request to server
        // Call API
        ApiService.apiService.signIn(loginRequestDTO)
                .enqueue(new Callback<BaseResponse<AuthenticationResponse>>(){
                    @Override
                    public void onResponse(Call<BaseResponse<AuthenticationResponse>> call, Response<BaseResponse<AuthenticationResponse>> response) {
                        if (response.isSuccessful()) {
                            Context context = SignIn.this;
                            SharePreferencesManager sharePreferencesManager = new SharePreferencesManager(context);
                            sharePreferencesManager.saveToken(response.body().getData().getToken());
                            // Đăng nhập thành công, chuyển đến MainActivity
                            showSuccessDialog();
                            redirectActivity(SignIn.this, MainActivity.class);
                        } else {
                            showErrorDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<AuthenticationResponse>> call, Throwable t) {
                        Log.e("SignIn", "Login failed", t);
                        showErrorDialog2();
                    }
                });

    }
    private void showSuccessDialog() {
        dialogSuccess2 = new Dialog(SignIn.this);
        dialogSuccess2.setContentView(R.layout.dialog_custom_success_signin);
        dialogSuccess2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtFail = dialogSuccess2.findViewById(R.id.failedDiscSignin2);

        // Set the dialog properties
        Window window = dialogSuccess2.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogSuccess2.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSuccess2.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang giao diện đăng nhập
                Intent intent = new Intent(SignIn.this, MainActivity.class);
                startActivity(intent);
                dialogSuccess2.dismiss();
                finish();
            }
        }, 2000);
    }

    private void showErrorDialog() {
        // Create and set up the dialog
        dialog = new Dialog(SignIn.this);
        dialog.setContentView(R.layout.dialog_custom_error);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Find the views inside the dialog layout
        MaterialButton failOK = dialog.findViewById(R.id.btn_ok_1);
        TextView txtFail = dialog.findViewById(R.id.failedDisc);

        // Set the dialog properties
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        // Set the click listener for the OK button
        failOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Make the dialog cancelable and set its layout parameters
        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Show the dialog
        dialog.show();
    }


    private void showErrorDialog2() {

        // Create and set up the dialog
        dialog2 = new Dialog(SignIn.this);
        dialog2.setContentView(R.layout.dialog_custom_error_2);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Find the views inside the dialog layout
        MaterialButton failOK2 = dialog2.findViewById(R.id.btn_ok_2);
        TextView txtFail2 = dialog2.findViewById(R.id.failedDisc2);

        // Set the dialog properties
        Window window = dialog2.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        // Set the click listener for the OK button
        failOK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        // Make the dialog cancelable and set its layout parameters
        dialog2.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Show the dialog
        dialog2.show();
    }

}