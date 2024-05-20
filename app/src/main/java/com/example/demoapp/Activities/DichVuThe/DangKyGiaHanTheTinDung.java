package com.example.demoapp.Activities.DichVuThe;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.demoapp.Activities.MainActivity;
import com.example.demoapp.R;

public class DangKyGiaHanTheTinDung extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_gia_han_the_tin_dung);
        Toolbar toolbar = findViewById(R.id.tool_bar_dang_ky_gia_han_the_tin_dung);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Lấy hình ảnh icon trở lại từ ImageView
        ImageView backButton = findViewById(R.id.toolbar_back_dang_ky_gia_han_the_tin_dung);
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
                startActivity(new Intent(DangKyGiaHanTheTinDung.this, MainActivity.class));
            }
        });
    }

}