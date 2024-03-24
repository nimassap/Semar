package com.example.semar5;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Dapatkan token perangkat
                    String token = task.getResult();
                    Log.d("FCM_TOKEN", "FCM token: " + token);
                    // Simpan atau gunakan token sesuai kebutuhan
                });
    }
}