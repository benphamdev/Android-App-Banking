package com.example.demoapp.Activities.admin.provinces;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Activities.admin.OnDeleteClickListener;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.ProvinceRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.ProvinceResponse;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinceActivity extends AppCompatActivity implements OnDeleteClickListener {
    private RecyclerView rcvProvince;
    private ProvinceAdapter provinceAdapter;
    private List<Province> provinces;
    private ImageView imgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        rcvProvince = findViewById(R.id.rcv_admin_province);
        imgEdit = findViewById(R.id.img_edit);
        rcvProvince.setLayoutManager(new LinearLayoutManager(this));
        provinceAdapter = new ProvinceAdapter(provinces);
        rcvProvince.setAdapter(provinceAdapter);
        toolbars();
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateProvinceDialog();
            }
        });
        getProvince();
    }

    private void toolbars() {
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_province);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_province);
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
                startActivity(new Intent(ProvinceActivity.this, AdminsActivity.class));
            }
        });
    }

    private void getProvince() {
        ApiService.apiService.getAllProvinces()
                .enqueue(new Callback<BaseResponse<List<ProvinceResponse>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<List<ProvinceResponse>>> call,
                            Response<BaseResponse<List<ProvinceResponse>>> response
                    ) {
                        if (response.isSuccessful()) {
                            List<ProvinceResponse> provinceResponseList =
                                    response.body()
                                            .getData();
                            if (provinceResponseList != null) {
                                provinces = new ArrayList<>();
                                for (ProvinceResponse provinceResponse : provinceResponseList) {
                                    provinces.add(new Province(provinceResponse.getId(),
                                            provinceResponse.getName()));
                                }
                                provinceAdapter.setProvinces(provinces);
                            }
                        } else {
                            try {
                                Log.e(
                                        "TAGprovince",
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
                            Call<BaseResponse<List<ProvinceResponse>>> call,
                            Throwable t
                    ) {

                        Log.e("E", t.getMessage());
                    }
                });
    }
    public void showCreateProvinceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProvinceActivity.this);
        builder.setTitle("Enter Province Name");
        final EditText input = new EditText(ProvinceActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString().trim();
                if (!newName.isEmpty()) {
                    createProvince(newName);
                } else {
                    Toast.makeText(ProvinceActivity.this, "Please enter a province name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public void createProvince(String name) {
        ProvinceRequest provinceRequest = new ProvinceRequest(name);

        ApiService.apiService.createProvince(provinceRequest).enqueue(new Callback<BaseResponse<ProvinceResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProvinceResponse>> call, Response<BaseResponse<ProvinceResponse>> response) {
                if (response.isSuccessful()) {
                    ProvinceResponse provinceResponse = response.body().getData();
                    Province newProvince = new Province(provinceResponse.getId(), provinceResponse.getName());
                    provinces.add(newProvince);
                    provinceAdapter.notifyDataSetChanged();
                    Toast.makeText(ProvinceActivity.this, "Province created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Log.e("TAG1", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ProvinceResponse>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        if (provinceAdapter != null) {
            provinceAdapter.deleteTextView(position);
        }

    }
}