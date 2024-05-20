package com.example.demoapp.Activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.demoapp.Activities.admin.accounts.AccountActivity;
import com.example.demoapp.Activities.admin.branchInfo.BranchInfoActivity;
import com.example.demoapp.Activities.admin.loan.LoanDetailActivity;
import com.example.demoapp.Activities.admin.loanDisburts.LoanDisbursementActivity;
import com.example.demoapp.Activities.admin.post.CreatePostActivity;
import com.example.demoapp.Activities.admin.post.PostActivity;
import com.example.demoapp.Activities.admin.provinces.ProvinceActivity;
import com.example.demoapp.Activities.admin.saving.SavingActivity;
import com.example.demoapp.Activities.admin.transactions.TransactionsActivity;
import com.example.demoapp.Activities.admin.user.UserActivity;
import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivityAdminsBinding;

public class AdminsActivity extends AppCompatActivity {

    CardView userAdmin, provinceAdmin, branchInfoAdmin, accountAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        setUI();
        click();
    }
    private void setUI(){
        userAdmin = findViewById(R.id.admin_user);
        provinceAdmin = findViewById(R.id.province_admin);
        branchInfoAdmin = findViewById(R.id.admin_branchinfo);
        accountAdmin = findViewById(R.id.admin_account);
    }
    private void click(){
        userAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminsActivity.this, UserActivity.class));
            }
        });
        provinceAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminsActivity.this, ProvinceActivity.class));
            }
        });
        branchInfoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminsActivity.this, CreatePostActivity.class));
            }
        });
        accountAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminsActivity.this, PostActivity.class));
            }
        });
    }
}