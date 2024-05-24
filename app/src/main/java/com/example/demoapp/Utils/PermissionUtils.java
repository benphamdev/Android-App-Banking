package com.example.demoapp.Utils;

import static com.example.demoapp.Utils.PermissionConst.CAMERA_PERMISSION_REQUEST_CODE;
import static com.example.demoapp.Utils.PermissionConst.GALLERY_PERMISSION_REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(
                context,
                permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions)
            if (!hasPermission(context, permission))
                return false;
        return true;
    }

    public static void requestPermission(Activity context, String permission, int requestCode) {
        ActivityCompat.requestPermissions(
                context,
                new String[]{permission},
                requestCode
        );
    }

    public static void requestPermissions(
            Activity activity, String[] permissions, int requestCode
    ) {
        ActivityCompat.requestPermissions(
                activity,
                permissions,
                requestCode
        );
    }

    public static void openCamera(
            Activity activity, ActivityResultLauncher<Intent> cameraLauncher
    ) {
        if (hasPermission(activity, Manifest.permission.CAMERA)) {
            launchCamera(activity, cameraLauncher);
        } else {
            requestPermission(activity, Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    public static void openGallery(
            Activity activity, ActivityResultLauncher<Intent> galleryLauncher
    ) {
        if (hasPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            launchGallery(galleryLauncher);
        } else {
            requestPermission(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    GALLERY_PERMISSION_REQUEST_CODE
            );
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public static void launchCamera(
            Activity activity, ActivityResultLauncher<Intent> cameraLauncher
    ) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
            cameraIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    activity.getContentResolver()
                            .insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    values
                            )
            );
            cameraLauncher.launch(cameraIntent);
        }
    }

    public static void launchGallery(ActivityResultLauncher<Intent> galleryLauncher) {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        galleryLauncher.launch(galleryIntent);
    }
}
