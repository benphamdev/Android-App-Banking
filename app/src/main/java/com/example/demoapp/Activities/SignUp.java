package com.example.demoapp.Activities;

import static com.example.demoapp.Utils.RedirectActivity.redirectActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.UserCreationRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.R;
import com.example.demoapp.Utils.RegistrationFormValidatorHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private TextView txtGoToSignIn;
    private Button btnSignup;
    SimpleDateFormat simpleDateFormat;
    Dialog dialogError, dialogSuccess;

    Calendar calendar;
    private RadioGroup genderRadioGroup;
    private RadioButton radioButtonNam, radioButtonNu;
    private TextInputEditText txtRegFirstName, txtRegLastName, txtRegEmail, txtRegPassword,
            txtRegOtherName, txtDayOfBirth, txtPhoneNumber, txtAddress, txtRegConfirm;
    private TextInputLayout txtRegFirstNameLayout, txtRegLastNameLayout, txtOtherNameLayout,
            txtDayOfBirthLayout, txtPhoneNumberLayout, txtAddressLayout, txtRegEmailLayout,
            txtRegPassLayout, txtRegConfirmLayout, txtRegGenderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_vcb);
        setUpUI();

        validateData();

        btnSignup.setOnClickListener(v -> {
            try {
                registerUser();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        txtGoToSignIn.setOnClickListener(v -> {
            redirectActivity(SignUp.this, SignIn.class);
            finish();
        });
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        txtDayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ngay1();
            }
        });

    }

    private void registerUser() throws JSONException {
        // GET DATA:
        String first_name = txtRegFirstName.getText()
                .toString();
        String last_name = txtRegLastName.getText()
                .toString();
        String other_name = txtRegOtherName.getText()
                .toString();
        String day_of_birth = txtDayOfBirth.getText()
                .toString();
        String phone_number = txtPhoneNumber.getText()
                .toString();
        String address = txtAddress.getText()
                .toString();
        String email = txtRegEmail.getText()
                .toString();
        String password = txtRegPassword.getText()
                .toString();
        final String[] gender = {null};
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.femaleRadioButton) {
                    gender[0] = "FEMALE";
                } else {
                    gender[0] = "MALE";
                }
            }
        });

        UserCreationRequest userCreationRequest = new UserCreationRequest(
                first_name,
                last_name,
                other_name,
                day_of_birth,
                "Male",
                address,
                email,
                phone_number,
                password
        );

        // Method post request to server
        // Call API
        ApiService.apiService.signUp(userCreationRequest)
                .enqueue(new Callback<BaseResponse<Long>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<Long>> call,
                            Response<BaseResponse<Long>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d("SUCCESS", "onResponse: " + response.body().toString());
                            showSuccessDialog();
//                            redirectActivity(SignUp.this, SignIn.class);
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody);
                                String errorMessage = jsonObject.optString("message");
                                showErrorDialog();
                                Log.d("TAG", "onResponse: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<Long>> call, Throwable t
                    ) {
                        Log.d("EEEEE", "onResponse: " + t.getMessage());
                    }
                });
    }

    public void setUpUI() {

        // INITIATE / HOOK VIEW COMPONENTS:
        txtGoToSignIn = findViewById(R.id.txt_go_to_sign_in);
        btnSignup = findViewById(R.id.btn_signup);

        // TEXT INPUT EDIT TEXT FIELDS:
        txtRegFirstName = findViewById(R.id.txt_first_name);
        txtRegLastName = findViewById(R.id.txt_last_name);
        txtRegOtherName = findViewById(R.id.txt_other_name);
        txtDayOfBirth = findViewById(R.id.txt_day_of_birth);
        txtAddress = findViewById(R.id.txt_address);
        txtPhoneNumber = findViewById(R.id.txt_phone_number_signup);
        txtRegEmail = findViewById(R.id.txt_email_sign_up);
        txtRegPassword = findViewById(R.id.txt_password_sign_up);
        txtRegConfirm = findViewById(R.id.txt_confirm);
        radioButtonNam = findViewById(R.id.maleRadioButton);
        radioButtonNu = findViewById(R.id.femaleRadioButton);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        // TEXT INPUT LAYOUTS:
        txtRegFirstNameLayout = findViewById(R.id.txt_first_name_layout);
        txtRegLastNameLayout = findViewById(R.id.txt_last_name_layout);
        txtOtherNameLayout = findViewById(R.id.txt_other_name_layout);
        txtDayOfBirthLayout = findViewById(R.id.txt_day_of_birth_layout);
        txtAddressLayout = findViewById(R.id.txt_address_layout);
        txtPhoneNumberLayout = findViewById(R.id.txt_phone_number_signup_layout);
        txtRegEmailLayout = findViewById(R.id.txt_email_layout_sign_up);
        txtRegPassLayout = findViewById(R.id.txt_password_layout_sign_up);
        txtRegConfirmLayout = findViewById(R.id.txt_confirm_layout);

    }

    public void validateData() {
        // VALIDATE FORM DATA / FIELDS:
        RegistrationFormValidatorHelper regFormValidator
                = new RegistrationFormValidatorHelper(
                txtRegFirstName,
                txtRegLastName,
                txtRegOtherName,
                genderRadioGroup,
                radioButtonNam,
                radioButtonNu,
                txtDayOfBirth,
                txtAddress,
                txtRegEmail,
                txtPhoneNumber,
                txtRegPassword,
                txtRegConfirm,
                txtRegFirstNameLayout,
                txtRegLastNameLayout,
                txtOtherNameLayout,
                txtDayOfBirthLayout,
                txtAddressLayout,
                txtRegEmailLayout,
                txtPhoneNumberLayout,
                txtRegPassLayout,
                txtRegConfirmLayout,
                btnSignup
        );

        txtRegFirstName.addTextChangedListener(regFormValidator);
        txtRegLastName.addTextChangedListener(regFormValidator);
        txtRegOtherName.addTextChangedListener(regFormValidator);
        txtDayOfBirth.addTextChangedListener(regFormValidator);
        radioButtonNu.addTextChangedListener(regFormValidator);
        radioButtonNam.addTextChangedListener(regFormValidator);
        txtAddress.addTextChangedListener(regFormValidator);
        txtRegEmail.addTextChangedListener(regFormValidator);
        txtPhoneNumber.addTextChangedListener(regFormValidator);
        txtRegPassword.addTextChangedListener(regFormValidator);
        txtRegConfirm.addTextChangedListener(regFormValidator);
    }

    public void Ngay1() {
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                txtDayOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, date);
        dialog.show();
    }

    private void showSuccessDialog() {
        dialogSuccess = new Dialog(SignUp.this);
        dialogSuccess.setContentView(R.layout.dialog_custom_error_login2);
        dialogSuccess.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtFail = dialogSuccess.findViewById(R.id.failedDiscLogin2);

        // Set the dialog properties
        Window window = dialogSuccess.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogSuccess.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialogSuccess.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang giao diện đăng nhập
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                dialogSuccess.dismiss();
                finish();
            }
        }, 2000);
    }

    private void showErrorDialog() {
        // Create and set up the dialog
        dialogError = new Dialog(SignUp.this);
        dialogError.setContentView(R.layout.dialog_custom_error_login1);
        dialogError.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Find the views inside the dialog layout
        MaterialButton failOK = dialogError.findViewById(R.id.btn_ok_login);
        TextView txtFail = dialogError.findViewById(R.id.failedDiscLogin);

        // Set the dialog properties
        Window window = dialogError.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        // Set the click listener for the OK button
        failOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });

        // Make the dialog cancelable and set its layout parameters
        dialogError.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        // Show the dialog
        dialogError.show();
    }

};