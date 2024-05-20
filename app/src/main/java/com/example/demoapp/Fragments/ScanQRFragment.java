package com.example.demoapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.demoapp.Activities.CreateQR;
import com.example.demoapp.databinding.FragmentScanQRBinding;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.InputStream;

public class ScanQRFragment extends Fragment {

    private FragmentScanQRBinding binding;
    private String textToType = "Scan QR to pay, transfer money, and withdraw cash at the ATM system!";
    private int index = 0;
    private long delay = 100;
    private Handler handler = new Handler();
    private ActivityResultLauncher<ScanOptions> qrCodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    setResult(result.getContents());
                }
            });
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            showCamera();
                        } else {
                            Toast.makeText(
                                            getActivity(),
                                            "Camera permission denied",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        }
                    }
            );
    private ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData()
                            .getData();
                    try {
                        InputStream imageStream = requireContext().getContentResolver().openInputStream(
                                selectedImageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        scanQRCodeFromBitmap(selectedImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(
                                        getActivity(),
                                        "Failed to open image",
                                        Toast.LENGTH_SHORT
                                )
                                .show();
                    }
                }
            }
    );
    private void scanQRCodeFromBitmap(Bitmap bitmap) {
        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(
                intArray,
                0,
                bitmap.getWidth(),
                0,
                0,
                bitmap.getWidth(),
                bitmap.getHeight()
        );
        RGBLuminanceSource source = new RGBLuminanceSource(
                bitmap.getWidth(),
                bitmap.getHeight(),
                intArray
        );
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(binaryBitmap);
            setResult(result.getText());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "No QR code found in image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void setResult(String contents) {
        binding.textResult.setText(contents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScanQRBinding.inflate(inflater, container, false);
        initViews();
        binding.btnTaoqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateQR.class));
            }
        });
        startTypingAnimation();
        return binding.getRoot();
    }
    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index <= textToType.length()) {
                    binding.txtTypingQr.setText(textToType.substring(0, index));
                    index++;
                    handler.postDelayed(this, delay);
                } else {
                    // Reset lại sau khi hoàn thành và bắt đầu lại từ đầu
                    index = 0;
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }

    private void initViews() {
        binding.btnScanqr.setOnClickListener(view -> {
            checkPermissionAndShowActivity(getActivity());
        });

        binding.btnScangallery.setOnClickListener(view -> {
            selectImageFromGallery();
        });

        binding.btnTaoqr.setOnClickListener(view -> {
            // Replace with your logic for creating QR code
            // Example: startActivity(new Intent(requireContext(), CreateQR.class));
        });
    }


    private void checkPermissionAndShowActivity(final @NonNull Context view) {
        if (ContextCompat.checkSelfPermission(
                view,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            showCamera();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(view, "Camera permission required", Toast.LENGTH_SHORT)
                    .show();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void showCamera() {
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan QR code");
        options.setCameraId(0);
        options.setBeepEnabled(false);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);
        qrCodeLauncher.launch(options);
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        selectImageLauncher.launch(intent);
    }

    private static final int REQUEST_CAMERA_PERMISSION = 1001;
}
