package com.example.demoapp.Activities.TietKiem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.demoapp.Activities.ChuyenTien.ChuyenTienTrongVCB;
import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Activities.TinDung.TatToanVayKhac;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.SavingRequest;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoTietKiem extends AppCompatActivity {
    Toolbar toolbar;
    ImageView backButton;
    TextView txtStk, txtSodu;
    EditText edtThoiGianGui, edtSoTienGui;
    Button btnTTGuiTK;
    private String fromAccountNumber;
    private int userId;
    private Long amount;
    private int duration;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mo_tiet_kiem);

        setUpUI();
        process();

        // Ẩn nút khi chưa chọn radio button
        btnTTGuiTK.setVisibility(View.GONE);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnTTGuiTK.setVisibility(View.VISIBLE);
                } else {
                    btnTTGuiTK.setVisibility(View.GONE);
                }
            }
        });
        btnTTGuiTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDepositConfirmationDialog();
            }
        });
    }
    private void showDepositConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận gửi tiết kiệm");
        builder.setMessage("Bạn có chắc chắn muốn gửi số tiền này vào tài khoản tiết kiệm không?");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savingApi();
                showDepositSuccessDialog();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Hủy thực hiện gửi tiết kiệm
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDepositSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gửi tiết kiệm thành công");
        builder.setMessage("Quá trình gửi tiền vào tài khoản tiết kiệm đã hoàn tất thành công.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MoTietKiem.this, MainActivity.class));
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void savingApi() {
        amount = Long.valueOf(edtSoTienGui.getText().toString());
        duration = Integer.parseInt(edtThoiGianGui.getText().toString());
        SavingRequest savingRequest = new SavingRequest(userId, amount, duration);
        Log.d("AAAAAA", amount + " " + userId + " " + duration);

        ApiService.apiService.createSavingAccount(savingRequest).enqueue(new Callback<BaseResponse<Saving>>() {
            @Override
            public void onResponse(Call<BaseResponse<Saving>> call, Response<BaseResponse<Saving>> response) {
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
            public void onFailure(Call<BaseResponse<Saving>> call, Throwable throwable) {
                Log.e("EEEE", throwable.getMessage());
            }
        });
    }

    private void process() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        Drawable drawable = backButton.getDrawable();

        // Thiết lập màu sắc cho icon
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white)); // Thay đổi your_color thành màu sắc bạn muốn
        }
        SharePreferencesManager manager = new SharePreferencesManager(MoTietKiem.this);
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
                startActivity(new Intent(MoTietKiem.this, MainActivity.class));
            }
        });
    }

    private void setUpUI() {
        toolbar = findViewById(R.id.tool_bar_mo_tiet_kiem);
        backButton = findViewById(R.id.toolbar_back_mo_tiet_kiem);
        btnTTGuiTK = findViewById(R.id.btn_tiep_tuc_gui_tk);
        txtStk = findViewById(R.id.tv_stk);
        edtSoTienGui = findViewById(R.id.tv_so_tien_gui);
        edtThoiGianGui = findViewById(R.id.edt_thoi_gian_gui);
        txtSodu = findViewById(R.id.tv_so_du_kd);
        radioButton = findViewById(R.id.radio_button);
    }
}