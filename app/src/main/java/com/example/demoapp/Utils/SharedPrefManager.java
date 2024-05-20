package com.example.demoapp.Utils;

import android.content.Context;

public class SharedPrefManager {
    //khỏi tạo các hằng key
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    //khoi tao constructor
    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(
            Context context
    ) {//ktra phiên bản mới đã được tạo hay chưa
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
}

