package com.example.demoapp.Activities.ChuyenTien;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

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
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.TransferRequest;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.io.IOException;
import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuyenTienTrongVCB extends AppCompatActivity {

    TextView stkChuyen, tvSoDu;
    EditText stkNhan, soTienChuyen;
    Button btnTiepTuc;
    private Toolbar toolbar;
    ImageView backButton;
    String toAccountNumber, fromAccountNumber;
    Long amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_tien_trong_vcb);

        setUpUI();
        process();
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTransferConfirmationDialog();
            }
        });


    }
    private void showTransferConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận chuyển tiền");
        builder.setMessage("Bạn có chắc chắn muốn thực hiện chuyển tiền không?");

        builder.setPositiveButton("Chuyển tiền", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                transferApi();
                showTransferSuccessDialog();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showTransferSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chuyển tiền thành công");
        builder.setMessage("Quá trình chuyển tiền đã hoàn tất thành công.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ChuyenTienTrongVCB.this, MainActivity.class));
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void transferApi() {
        toAccountNumber = stkNhan.getText().toString();
        amount = Long.valueOf(soTienChuyen.getText().toString());
        Log.d("AAAA", toAccountNumber+ " " + fromAccountNumber + " " + amount);
        TransferRequest request = new TransferRequest(fromAccountNumber,toAccountNumber, amount);
        ApiService.apiService.transfer(request).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "CK : " + response.body().toString());
                } else {
                    try {
                        Log.d("TAG", "CK : " + response.errorBody()
                                .string());

                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AccountInfoResponse>> call, Throwable throwable) {
                Log.d("Catch", throwable.getMessage());
            }
        });
    }

    private void process() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Lấy hình ảnh icon trở lại từ ImageView
        Drawable drawable = backButton.getDrawable();

        // Thiết lập màu sắc cho icon
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white)); // Thay đổi your_color thành màu sắc bạn muốn
        }
        SharePreferencesManager manager = new SharePreferencesManager(ChuyenTienTrongVCB.this);

        ApiService.apiService.getAccountById(manager.getUserId()).enqueue(new Callback<BaseResponse<AccountInfoResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AccountInfoResponse>> call, Response<BaseResponse<AccountInfoResponse>> response) {
                if(response.isSuccessful()) {
                    Log.d("SUCCESS", "onResponse: " + response.body().toString());
                    AccountInfoResponse data = response.body().getData();
                    String sodu = data.getAccountBalance().toString();
                    fromAccountNumber = data.getAccountNumber().toString();

                    tvSoDu.setText(sodu);
                    stkChuyen.setText(data.getAccountNumber());
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
                startActivity(new Intent(ChuyenTienTrongVCB.this, MainActivity.class));
            }
        });

    }

    private void setUpUI() {
        toolbar = findViewById(R.id.tool_bar_chuyen_tien_trong_VCB);
        backButton = findViewById(R.id.toolbar_back_trong_vcb);
        btnTiepTuc = findViewById(R.id.btn_tiep_tuc);
        stkChuyen = findViewById(R.id.stk_chuyen);
        stkNhan = findViewById(R.id.stk_nhan);
        tvSoDu = findViewById(R.id.tv_so_du);
        soTienChuyen = findViewById(R.id.tv_so_tien_chuyen);
    }
}