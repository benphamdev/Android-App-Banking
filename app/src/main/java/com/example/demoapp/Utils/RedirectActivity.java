package com.example.demoapp.Utils;

import android.app.Activity;
import android.content.Intent;

public interface RedirectActivity {
    static void redirectActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }
}
