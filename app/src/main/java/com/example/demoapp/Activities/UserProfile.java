package com.example.demoapp.Activities;

import static com.example.demoapp.Utils.PermissionConst.CAMERA_PERMISSION_REQUEST_CODE;
import static com.example.demoapp.Utils.PermissionConst.GALLERY_PERMISSION_REQUEST_CODE;
import static com.example.demoapp.Utils.PermissionUtils.launchCamera;
import static com.example.demoapp.Utils.PermissionUtils.launchGallery;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
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
import com.example.demoapp.Models.dto.response.BaseResponse;
import com.example.demoapp.Models.dto.response.UserResponse;
import com.example.demoapp.R;
import com.example.demoapp.Utils.PermissionUtils;
import com.example.demoapp.Utils.RealPathUtil;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfile extends AppCompatActivity {

    public static Uri imageUri;
    CircleImageView imgProfile;
    LinearLayout xemAvatar, chupAvatar, chonAvatar;
    ApiService apiService;
    Dialog dialog;
    private Intent cameraIntent, galleryIntent;
    private ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        imgProfile = findViewById(R.id.img_view_user_avt);

        imgProfile.setOnClickListener(v -> showBottomDialog());

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imgProfile.setImageURI(imageUri);
                        //dialog.dismiss();
                        UploadImage1();
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
                        UploadImage1();
                    }
                }
        );

        PermissionUtils.openCamera(this, cameraLauncher);
        PermissionUtils.openGallery(this, galleryLauncher);
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
            launchCamera(this, cameraLauncher);
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
                launchCamera(this, cameraLauncher);
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission deny", Toast.LENGTH_SHORT)
                     .show();
            }
        }

        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch gallery intent
                launchGallery(galleryLauncher);
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Permission deny", Toast.LENGTH_SHORT)
                     .show();
            }
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
        dialog.getWindow()
              .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow()
              .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow()
              .getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow()
              .setGravity(Gravity.BOTTOM);
    }
}