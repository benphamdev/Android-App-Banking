package com.example.demoapp.Activities.admin.accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Activities.admin.OnDeleteClickListener;
import com.example.demoapp.Activities.admin.branchInfo.BranchInfoActivity;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity implements OnDeleteClickListener {

    private RecyclerView rcvAccount;
    private AccountInfoAdapter accountInfoAdapter;
    private List<AccountInfoResponse> accountInfoResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        rcvAccount = findViewById(R.id.rcv_admin_account);
        rcvAccount.setLayoutManager(new LinearLayoutManager(this));
        accountInfoAdapter = new AccountInfoAdapter(accountInfoResponses);
        rcvAccount.setAdapter(accountInfoAdapter);
        toolbars();
        getAccounts();
    }
    private void toolbars() {
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_account);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_account);
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
                startActivity(new Intent(AccountActivity.this, AdminsActivity.class));
            }
        });
    }
    private void getAccounts() {
        ApiService.apiService.getAccount()
                .enqueue(new Callback<BaseResponse<List<AccountInfoResponse>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<List<AccountInfoResponse>>> call,
                            Response<BaseResponse<List<AccountInfoResponse>>> response
                    ) {
                        if (response.isSuccessful()) {
                            List<AccountInfoResponse> accountInfoList = response.body()
                                    .getData();
                            if (accountInfoList != null && !accountInfoList.isEmpty()) {
                                // Initialize the adapter with the account info list
                                AccountInfoAdapter accountInfoAdapter =
                                        new AccountInfoAdapter(accountInfoList);
                                // Set adapter to RecyclerView
                                rcvAccount.setAdapter(accountInfoAdapter);
                            }
                        } else {
                            try {
                                Log.e(
                                        "TagAccount",
                                        response.errorBody()
                                                .toString()
                                );
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<List<AccountInfoResponse>>> call,
                            Throwable t
                    ) {
                        Log.e("E:", t.getMessage());
                    }
                });
    }

    @Override
    public void onDeleteClick(int position) {
        if (accountInfoAdapter != null) {
            accountInfoAdapter.deleteAccount(position);
        }
    }
}