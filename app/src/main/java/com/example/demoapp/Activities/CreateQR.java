package com.example.demoapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.demoapp.Fragments.ScanQRFragment;
import com.example.demoapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateQR extends AppCompatActivity {

    private EditText QRstk, QRsotien, QRnoidung;
    private Button btnGenerate;
    private ImageView imgQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_qr);
        Toolbar toolbar = findViewById(R.id.tool_bar_tao_qr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        // Lấy hình ảnh icon trở lại từ ImageView
        ImageView backButton = findViewById(R.id.toolbar_back_tao_qr);
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
                startActivity(new Intent(CreateQR.this, ScanQRFragment.class));
            }
        });
        QRstk = findViewById(R.id.stk_qr);
        QRnoidung = findViewById(R.id.noidung_qr);
        QRsotien = findViewById(R.id.sotien_qr);
        btnGenerate = findViewById(R.id.btn_generate);
        imgQR = findViewById(R.id.img_qr);
        btnGenerate.setOnClickListener(view -> generateQRCode());
    }

    private void generateQRCode() {
        String stk = QRstk.getText()
                          .toString();
        String sotien = QRnoidung.getText()
                                 .toString();
        String noiDung = QRsotien.getText()
                                 .toString();
        String combinedText =
                stk + " " + sotien + " " + noiDung; // Hoặc bạn có thể sử dụng bất kỳ ký tự nào khác để kết hợp

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(
                    combinedText,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}