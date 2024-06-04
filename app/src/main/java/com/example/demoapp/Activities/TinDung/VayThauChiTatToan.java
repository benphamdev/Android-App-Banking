package com.example.demoapp.Activities.TinDung;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Activities.TietKiem.MoTietKiem;
import com.example.demoapp.Fragments.HomeFragment;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.LoanDetailRequest;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.entity.LoanInfo;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import java.io.IOException;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VayThauChiTatToan extends AppCompatActivity {

    Toolbar toolbar;
    ImageView backButton;
    private String fromAccountNumber;
    private int userId;
    private TextView txtSodu, txtStk;
    private EditText edtSoTienVay;
    private EditText edtThoiGianVay;
    private TextView edtSoThamChieu;
    private Button btnTiepTucVay;
    private int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vay_thau_chi_tat_toan);

        setUpUI();
        process();
        btnTiepTucVay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận việc vay trong ngân hàng");
        builder.setMessage("Bạn có chắc chắn muốn vay tiền trong ngân hàng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loanApi();
                showLoanApprovalSuccessDialog();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showLoanApprovalSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kết quả vay tiền");
        builder.setMessage("Yêu cầu vay của bạn đang được phê duyệt.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(VayThauChiTatToan.this, MainActivity.class));
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loanApi() {
        int month = Integer.parseInt(edtThoiGianVay.getText().toString());
        Long loanAmount = Long.valueOf(edtSoTienVay.getText().toString());

        double interestRate = 5.5;
        LoanInfo loanInfo = new LoanInfo(loanAmount, userId , interestRate);
        Log.d("TAGGGGGGGGGG", randomNumber + " " + userId + " " + interestRate + " " + month + " " + loanAmount);
        LoanDetailRequest loanDetail = new LoanDetailRequest((long) randomNumber,month ,loanInfo);
        ApiService.apiService.saveLoanDetail(loanDetail).enqueue(new Callback<BaseResponse<LoanDetail>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoanDetail>> call, Response<BaseResponse<LoanDetail>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "TK : " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "TK : " + response.errorBody()
                                .string());

                    } catch(IOException e) {
                        Log.e("EE", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoanDetail>> call, Throwable throwable) {
                Log.e("EEEE", throwable.getMessage());
            }
        });
    }

    private void process() {
        Random random = new Random();
        int min = 10000000;
        int max = 99999999;
        randomNumber = random.nextInt(max - min + 1) + min;
        String tmp = String.valueOf(randomNumber);
        edtSoThamChieu.setText(tmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Lấy hình ảnh icon trở lại từ ImageView
        Drawable drawable = backButton.getDrawable();

        // Thiết lập màu sắc cho icon
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white)); // Thay đổi your_color thành màu sắc bạn muốn
        }

        SharePreferencesManager manager = new SharePreferencesManager(VayThauChiTatToan.this);
        userId = manager.getUserId();
        ApiService.apiService.getAccountById(userId).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "onResponse: " + response.body().toString());
                    AccountInfoResponse data = response.body().getData();
                    String sodu = data.getAccountBalance().toString();
                    fromAccountNumber = data.getAccountNumber().toString();

                    txtSodu.setText(sodu);
                    txtStk.setText(data.getAccountNumber());
                } else {
                    try {
                        Log.d("TAG", "onResponse: " + response.errorBody()
                                .string());

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<AccountInfoResponse>> call, Throwable throwable) {

            }
        });

        // Đặt hình ảnh đã được thiết lập màu sắc làm biểu tượng trở lại trên ImageView
        backButton.setImageDrawable(drawable);

        // Bắt sự kiện click của ImageView
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VayThauChiTatToan.this, HomeFragment.class));
            }
        });
    }

    private void setUpUI() {
        toolbar = findViewById(R.id.tool_bar_tat_toan_vay_khac);
        backButton = findViewById(R.id.toolbar_back_tat_toan_vay_khac);
        txtStk = findViewById(R.id.tv_so_tai_khoan_vay);
        txtSodu = findViewById(R.id.tv_so_du_vay);
        edtSoTienVay = findViewById(R.id.edt_so_tien_vay);
        edtThoiGianVay = findViewById(R.id.edt_thoi_gian_vay);
        edtSoThamChieu = findViewById(R.id.edt_so_tham_chieu);
        btnTiepTucVay = findViewById(R.id.btn_tiep_tuc_vay);
    }

}