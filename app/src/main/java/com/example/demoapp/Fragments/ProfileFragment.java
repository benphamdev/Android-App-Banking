package com.example.demoapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.demoapp.Activities.DatLaiMatKhau;
import com.example.demoapp.Activities.SignIn;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.HttpRequest.RetrofitClient;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;
import com.example.demoapp.Utils.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 12;
    public static Uri imageUri;
    private CircleImageView imgProfile;
    private LinearLayout xemAvatar, chupAvatar, chonAvatar;
    private ApiService apiService;
    private Dialog dialog;

    private TextView textViewInfo;
    private Intent cameraIntent, galleryIntent;
    private ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;
    private AppCompatButton appCompatButtonDoiMatKhau,
            appCompatButtonDangXuat;
    private SharedPreferences sharedPreferences;
    private String idUser;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setUpUi(view);
        appCompatButtonDoiMatKhau.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                DatLaiMatKhau.class
        )));
        appCompatButtonDangXuat.setOnClickListener(v -> startActivity(new Intent(
                getActivity(),
                SignIn.class
        )));
        imgProfile = view.findViewById(R.id.img_view_user_avt);
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
                            Toast.makeText(getActivity(), "Don't choose image", Toast.LENGTH_SHORT)
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
        getMyProfile();

        return view;
    }

    public void setUpUi(View view) {
        appCompatButtonDoiMatKhau = view.findViewById(R.id.doi_mat_khau);
        appCompatButtonDangXuat = view.findViewById(R.id.dang_xuat);
        textViewInfo = view.findViewById(R.id.txt_view_user_name_profile);
    }

    public void UploadImage1() {
        Log.d("oke", "UploadImage1: ");
        if (imageUri == null)
            return;

        Log.d("oke", "UploadImage2: ");
        // Create a Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofit();

        // Create a RequestBody instance from the user_id
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), idUser);

        // Create a file instance from the Uri
        String realPath = RealPathUtil.getRealPath(getActivity(), imageUri);
        File file = new File(realPath);
        // Print the file size
//        file = compressImage(file);
        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        Log.d("Upload", "File size: " + fileSizeInMB + " MB");

        // Create a RequestBody instance from the file
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(getActivity().getContentResolver()
                        .getType(imageUri)),
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
                                            getActivity(),
                                            "Upload success",
                                            Toast.LENGTH_SHORT
                                    )
                                    .show();
                        } else {
                            // Handle the error
                            Toast.makeText(
                                            getActivity(),
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
                        Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT)
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
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
                Toast.makeText(getActivity(), "Permission deny", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch gallery intent
                launchGallery();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(getActivity(), "Permission deny", Toast.LENGTH_SHORT)
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
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
            imageUri = getActivity().getContentResolver()
                    .insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values
                    );
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraLauncher.launch(cameraIntent);
        }
    }

    private void showBottomDialog() {

        dialog = new Dialog(getActivity());
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

    public void getMyProfile() {

        Context context = getActivity();
        SharePreferencesManager sharePreferencesManager = new SharePreferencesManager(context);
        String tokenApi = sharePreferencesManager.getToken();
        SharePreferencesManager manager = new SharePreferencesManager(context);
        String tokenApp = manager.getTokenApp();
        SharePreferencesManager manager1 = new SharePreferencesManager(context);

        int id = manager1.getUserId();

        Log.d("TOKENNNNNN", tokenApp);

        ApiService.apiService.updatePhoneToken(id, tokenApp)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<Void>> call,
                            Response<BaseResponse<Void>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    "tokenApp: " + response.body()
                                            .toString()
                            );
                        } else {
                            try {
                                Log.d("TAG", "tokenApp: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<Void>> call, Throwable throwable
                    ) {
                        Log.e("E:", throwable.getMessage());
                    }
                });

        ApiService.apiService.getMyProfile("Bearer " + tokenApi)
                .enqueue(new Callback<BaseResponse<UserResponse>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(
                            @NonNull Call<BaseResponse<UserResponse>> call,
                            @NonNull Response<BaseResponse<UserResponse>> response
                    ) {
                        if (response.isSuccessful()) {
                            Log.d(
                                    "SUCCESS",
                                    "onResponse: " + response.body()
                                            .toString()
                            );
                            UserResponse userResponse = response.body()
                                    .getData();

                            // set full name for user
                            textViewInfo.setText(userResponse.getFirstName() + " " +
                                    userResponse.getLastName() + " " +
                                    userResponse.getOtherName());

                            //  set image avatar for user
                            if (userResponse.getProfilePicture() != null) {
                                String avatar = userResponse.getProfilePicture();
                                Glide.with(ProfileFragment.this)
                                        .load(avatar)
                                        .into(imgProfile);
                            }

                            // save userid in SharePreferencesManager
                            SharePreferencesManager userId =
                                    new SharePreferencesManager(context);

                            userId.saveUserId(response.body()
                                    .getData()
                                    .getId());

                            idUser = String.valueOf(response.body()
                                    .getData()
                                    .getId());

                        } else {
                            try {
                                Log.d("TAG", "onResponse: " + response.errorBody()
                                        .string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<BaseResponse<UserResponse>> call,
                            @NonNull Throwable throwable
                    ) {
                        Log.e("E:", Objects.requireNonNull(throwable.getMessage()));
                    }
                });
    }
}