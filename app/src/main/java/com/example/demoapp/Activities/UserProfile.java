package com.example.demoapp.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.HttpRequest.RetrofitClient;
import com.example.demoapp.Models.Dto.Requests.PostCreationRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.R;
import com.example.demoapp.Utils.RealPathUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfile extends AppCompatActivity {
    CircleImageView imgProfile;
    LinearLayout xemAvatar, chupAvatar, chonAvatar;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 12;
    public static Uri imageUri;
    private Intent cameraIntent, galleryIntent;
    private ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;
    ApiService apiService;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imgProfile = findViewById(R.id.img_view_user_avt);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }

        });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imgProfile.setImageURI(imageUri);
                        //dialog.dismiss();
                        createPost();
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
                        imgProfile.setImageURI(imageUri);
                        //dialog.dismiss();
                        createPost();
                    }
                }
        );
    }
    public void UploadImage1() {
        Log.d("oke", "UploadImage1: ");
        if (imageUri == null)
            return;

        Log.d("oke", "UploadImage2: ");
        // Create a Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofit();

        // Create a RequestBody instance from the user_id
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), "2");

        // Create a file instance from the Uri
        String realPath = RealPathUtil.getRealPath(this, imageUri);
        File file = new File(realPath);
        // Print the file size
//        file = compressImage(file);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        Log.d("Upload", "File size: " + fileSizeInMB + " MB");

        // Create a RequestBody instance from the file
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(getContentResolver().getType(imageUri)),
                file
        );

        // Create a MultipartBody.Part from the file RequestBody
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "profilePicture",
                file.getName(),
                requestFile
        );

        // Get the API interface
        apiService = RetrofitClient.getRetrofit()
                .create(ApiService.class);

        // Call the API
        apiService.uploadImage(userIdBody, body)
                .enqueue(new Callback<BaseResponse<UserResponse>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<UserResponse>> call,
                            Response<BaseResponse<UserResponse>> response
                    ) {
                        if (response.isSuccessful()) {
                            // Handle the response
                            Toast.makeText(
                                            UserProfile.this,
                                            "Upload success",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        } else {
                            // Handle the error
                            Toast.makeText(
                                            UserProfile.this,
                                            "Upload failed",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                            Log.e("Error2", response.message());
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<UserResponse>> call, Throwable t
                    ) {
                        // Handle the error
                        Toast.makeText(UserProfile.this, "Upload failed", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Error1", t.getMessage());
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE
            );
        } else {
            // Permission already granted, launch camera intent
            launchCamera();
        }
    }
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



    private void showBottomDialog() {

        dialog = new Dialog(this);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_profile);

        xemAvatar = dialog.findViewById(R.id.linear_xem_avt);
        chupAvatar = dialog.findViewById(R.id.linear_chup_avt);
        chonAvatar = dialog.findViewById(R.id.linear_chon_avt);
        chupAvatar.setOnClickListener(v -> dispatchTakePhoto());
        chonAvatar.setOnClickListener(v -> dispatchTakePhotoGallery());
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    public void createPost() {
        // Create a Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofit();

        Gson gson = new Gson();
        PostCreationRequest post = new PostCreationRequest(
                "Test Post",
                "This is a test post.",
                Collections.singletonList(10)
        );

        String postJson = gson.toJson(post);

        // Create a RequestBody instance from the post
        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), postJson);

        // Create a file instance from the Uri
        String realPath = RealPathUtil.getRealPath(this, imageUri);
        File file = new File(realPath);
        // Print the file size
//        file = compressImage(file);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        Log.d("Upload", "File size: " + fileSizeInMB + " MB");

        // Create a RequestBody instance from the file
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(getContentResolver().getType(imageUri)),
                file
        );
        // Create a MultipartBody.Part from the file RequestBody
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "file",
                file.getName(),
                requestFile
        );

        // Get the API interface
        apiService = RetrofitClient.getRetrofit()
                .create(ApiService.class);

        // Call the API
        apiService.createPost(postBody, body)
                .enqueue(new Callback<BaseResponse<com.example.demoapp.Models.Dto.entity.Post>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<com.example.demoapp.Models.Dto.entity.Post>> call,
                            Response<BaseResponse<com.example.demoapp.Models.Dto.entity.Post>> response
                    ) {
                        if (response.isSuccessful()) {
                            // Handle the response
                            Toast.makeText(
                                            UserProfile.this,
                                            "Upload success",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        } else {
                            // Handle the error
                            Toast.makeText(
                                            UserProfile.this,
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
                        // Handle the error
                        Toast.makeText(UserProfile.this, "Upload failed", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Error1", t.getMessage());
                    }
                });
    }
}