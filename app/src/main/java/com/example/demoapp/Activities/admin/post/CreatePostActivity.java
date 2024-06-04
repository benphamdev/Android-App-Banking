package com.example.demoapp.Activities.admin.post;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.HttpRequest.RetrofitClient;
import com.example.demoapp.Models.Dto.Requests.PostCreationRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.Models.Dto.entity.Post;
import com.example.demoapp.R;
import com.example.demoapp.Utils.RealPathUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePostActivity extends AppCompatActivity {
    private Uri imageUri;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 12;
    private ImageView imgPost;
    ImageView imgCam, imgGallery;
    Dialog dialog;
    int id1 = 0;
    private Button btnUp;
    ApiService apiService;
    private Button btnUpLoad, btnHuy;
    private Intent cameraIntent, galleryIntent;
    private ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        imgPost = findViewById(R.id.img_post);
        btnUp = findViewById(R.id.btn_up);
        btnUpLoad = findViewById(R.id.btn_upload);
        btnHuy = findViewById(R.id.btn_huy);
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imgPost.setImageURI(imageUri);
                        dialog.dismiss();

                    }
                }
        );

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            Toast.makeText(this, "Don't choose image", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        imageUri = data.getData();
                        imgPost.setImageURI(imageUri);
                        dialog.dismiss();
                        createPost();
                    }
                }
        );
        btnUp.setOnClickListener(v -> {
            showCustomDialog();
        });

        btnUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPost();
            }
        });
    }

    private void dispatchTakePhotoGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void dispatchTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE
            );
        } else {
            // Permission already granted, launch camera intent
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch camera intent
                launchCamera();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission deny", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch gallery intent
                launchGallery();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission deny", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchGallery() {
        galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        galleryLauncher.launch(galleryIntent);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void launchCamera() {
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
            imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
            );
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(cameraIntent);
        }
    }

    private void showCustomDialog() {
        // Create dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_create_post);

        // Find components in the dialog layout
        imgCam = dialog.findViewById(R.id.image_camera);
        imgGallery = dialog.findViewById(R.id.image_folder);
        Button buttonCancel = dialog.findViewById(R.id.button_cancel);

        imgCam.setOnClickListener(v -> dispatchTakePhoto());

        imgGallery.setOnClickListener(v -> dispatchTakePhotoGallery());
        // Set click listener for the Cancel button
        buttonCancel.setOnClickListener(v -> {
            // Dismiss the dialog when Cancel button is clicked
            dialog.dismiss();
        });

        // Show dialog
        dialog.show();
    }


    public void createPost() {
        Retrofit retrofit = RetrofitClient.getRetrofit();

        Gson gson = new Gson();
        PostCreationRequest post = new PostCreationRequest(
                "Test Post",
                "This is a test post.",
                Collections.singletonList(10)
        );

        String postJson = gson.toJson(post);
        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), postJson);
        String realPath = RealPathUtil.getRealPath(this, imageUri);
        File file = new File(realPath);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        Log.d("Upload", "File size: " + fileSizeInMB + " MB");
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(getContentResolver().getType(imageUri)),
                file
        );
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "file",
                file.getName(),
                requestFile
        );
        ApiService apiService = RetrofitClient.getRetrofit()
                .create(ApiService.class);
        apiService.createPost(postBody, body)
                .enqueue(new Callback<BaseResponse<Post>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<Post>> call,
                            Response<BaseResponse<Post>> response
                    ) {
                        if (response.isSuccessful()) {
                            Toast.makeText(
                                            CreatePostActivity.this,
                                            "Upload success",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        } else {
                            Toast.makeText(
                                            CreatePostActivity.this,
                                            "Upload failed",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                            Log.e("Error2", response.message());
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<com.example.demoapp.Models.Dto.entity.Post>> call,
                            Throwable t
                    ) {
                        Toast.makeText(CreatePostActivity.this, "Upload failed", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Error1", t.getMessage());
                    }
                });
    }
}