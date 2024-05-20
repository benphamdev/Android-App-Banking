package com.example.demoapp.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    ActionBar actionBar;
    private SharedPreferences prefs;
    private TextInputEditText txtProfFirstName, txtProfLastName, txtProfEmail;
    private Button profileLogoutBtn;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpUI();
        // get text from share preferences
        setUserDetailsInEditTextFields();

        profileLogoutBtn.setOnClickListener(v -> logUserOut());

        setUserDetailsInEditTextFields();
    }

    public void setUpUI() {
//        actionBar = getSupportActionBar();
//        actionBar.setTitle("Profile");

        txtProfFirstName = findViewById(R.id.txt_prof_first_name);
        txtProfLastName = findViewById(R.id.txt_prof_last_name);
        txtProfEmail = findViewById(R.id.txt_prof_email);
        profileLogoutBtn = findViewById(R.id.profile_logout_btn);
    }

    public void setUserDetailsInEditTextFields() {
//        prefs = getSharedPreferences(
//                StringResourceHelper.getUserDetailPrefName(),
//                MODE_PRIVATE
//        );

        txtProfFirstName.setText(prefs.getString("first_name", ""));
        txtProfLastName.setText(prefs.getString("last_name", ""));
        txtProfEmail.setText(prefs.getString("email", ""));
    }

    public void logUserOut() {
        clearPreferences();
//        redirectActivity(ProfileActivity.this, LogInVCB.class);
//        Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT)
//             .show();
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

}
