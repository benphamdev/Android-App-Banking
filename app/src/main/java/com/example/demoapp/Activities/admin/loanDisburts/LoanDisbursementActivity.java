package com.example.demoapp.Activities.admin.loanDisburts;

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
import android.widget.Toast;

import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Activities.admin.OnDeleteClickListener;
import com.example.demoapp.Activities.admin.loan.LoanDetailActivity;
import com.example.demoapp.Activities.admin.loan.LoanDetailAdapter;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.entity.LoanDisbursement;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanDisbursementActivity extends AppCompatActivity implements OnDeleteClickListener {
    private RecyclerView rcvLoanBurse;
    private LoanDisbursementAdapter loanDisbursementAdapter;
    private List<LoanDisbursement> loanDisbursementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_disbursement);
        rcvLoanBurse = findViewById(R.id.rcv_loan_bursement);
        rcvLoanBurse.setLayoutManager(new LinearLayoutManager(this));
        toolbars();
        loadDataFromApi();

    }
    private void toolbars() {
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_loanburse);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_loanburse);
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
                startActivity(new Intent(LoanDisbursementActivity.this, AdminsActivity.class));
            }
        });
    }
    private void loadDataFromApi() {
        ApiService.apiService.getLoanDisbursements().enqueue(new Callback<BaseResponse<List<LoanDisbursement>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<LoanDisbursement>>> call, Response<BaseResponse<List<LoanDisbursement>>> response) {
                if (response.isSuccessful()) {
                    loanDisbursementList = response.body().getData();
                    if (loanDisbursementList != null) {
                        showDataOnRecyclerView(loanDisbursementList);
                    }
                } else {
                    try {
                        String responseBodyString = response.errorBody().string();
                        Log.e("TAG", responseBodyString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<LoanDisbursement>>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(LoanDisbursementActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDataOnRecyclerView(List<LoanDisbursement> loanBurse) {
        if (loanDisbursementAdapter == null) {
            loanDisbursementAdapter = new LoanDisbursementAdapter(loanBurse);
            rcvLoanBurse.setAdapter(loanDisbursementAdapter);
        } else {
            loanDisbursementAdapter.setLoanDisbursementList(loanBurse);
            loanDisbursementAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        if(loanDisbursementAdapter != null){
            loanDisbursementAdapter.deleteLoanBurse(position);
        }
    }
}