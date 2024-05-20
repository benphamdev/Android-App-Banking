package com.example.demoapp.Activities.ChuyenTien;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.Adapters.MauThiepAdapter;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuaTang extends AppCompatActivity {

    private RecyclerView rcvMauThiep;
    private MauThiepAdapter mauThiepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qua_tang);
        rcvMauThiep = findViewById(R.id.rcv_qua_tang);
        xuLyRcvMauThiep();
        Toolbar toolbar = findViewById(R.id.tool_bar_qua_tang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Lấy hình ảnh icon trở lại từ ImageView
        ImageView backButton = findViewById(R.id.toolbar_back_qua_tang);
        Drawable drawable = backButton.getDrawable();

        // Thiết lập màu sắc cho icon
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(this, R.color.white)
            ); // Thay đổi your_color thành màu sắc bạn muốn
        }

        // Đặt hình ảnh đã được thiết lập màu sắc làm biểu tượng trở lại trên ImageView
        backButton.setImageDrawable(drawable);

        // Bắt sự kiện click của ImageView
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuaTang.this, MainActivity.class));
            }
        });
    }

    private void xuLyRcvMauThiep() {
        mauThiepAdapter = new MauThiepAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                RecyclerView.HORIZONTAL,
                false
        );
        rcvMauThiep.setLayoutManager(linearLayoutManager);
        mauThiepAdapter.setData(getListMauThiep());
        rcvMauThiep.setAdapter(mauThiepAdapter);

    }

    private List<MauThiep> getListMauThiep() {
        List<MauThiep> list = new ArrayList<>();
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        list.add(new MauThiep(R.drawable.thiep));
        return list;
    }

}