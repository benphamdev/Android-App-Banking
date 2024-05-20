package com.example.demoapp.Activities.admin.loan;

import static java.security.AccessController.getContext;

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
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoanDetailActivity extends AppCompatActivity implements OnDeleteClickListener {

    private RecyclerView rcvLoan;
    private LoanDetailAdapter loanDetailAdapter;
    List<LoanDetail> loanDetails = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
        rcvLoan = findViewById(R.id.rcv_loan_detail);
        rcvLoan.setLayoutManager(new LinearLayoutManager(this));
        toolbars();
        loadDataFromApi();
    }
    private void toolbars() {
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_loandetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_loandetail);
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
                startActivity(new Intent(LoanDetailActivity.this, AdminsActivity.class));
            }
        });
    }
    private void loadDataFromApi() {
        ApiService.apiService.getAllLoanDetails().enqueue(new Callback<BaseResponse<List<LoanDetail>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<LoanDetail>>> call, Response<BaseResponse<List<LoanDetail>>> response) {
                if (response.isSuccessful()) {
                    loanDetails = response.body().getData();
                    if (loanDetails != null) {
                        showDataOnRecyclerView(loanDetails);
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
            public void onFailure(Call<BaseResponse<List<LoanDetail>>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                Toast.makeText(LoanDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDataOnRecyclerView(List<LoanDetail> loanDetails) {
        if (loanDetailAdapter == null) {
            loanDetailAdapter = new LoanDetailAdapter(loanDetails);
            rcvLoan.setAdapter(loanDetailAdapter);
        } else {
            loanDetailAdapter.setLoanDetailList(loanDetails);
            loanDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        if(loanDetailAdapter != null){
            loanDetailAdapter.deleteLoan(position);
        }
    }
}