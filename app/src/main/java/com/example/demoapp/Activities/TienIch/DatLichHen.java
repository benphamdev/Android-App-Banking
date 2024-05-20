package com.example.demoapp.Activities.TienIch;

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

public class DatLichHen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_hen);
        Toolbar toolbar = findViewById(R.id.tool_bar_dat_lich_hen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_dat_lich_hen);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white));
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DatLichHen.this, MainActivity.class));
            }
        });
    }
}