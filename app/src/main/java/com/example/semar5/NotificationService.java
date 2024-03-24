package com.example.semar5;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.semar5.ModelResponse.NotificationModel;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.util.List;

public class NotificationService extends Service {

    private static final long POLL_INTERVAL = 30 * 60 * 1000; // 30 minutes

    private Handler handler = new Handler();
    private Runnable notificationChecker = new Runnable() {
        @Override
        public void run() {
            checkForNewNotifications();

            handler.postDelayed(this, POLL_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(notificationChecker);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(notificationChecker);
        super.onDestroy();
    }

    private void checkForNewNotifications() {
        // Retrieve and compare notifications, and display if needed
        // You can use SharedPreferenceHelper to retrieve notifications and determine if admin
        // If new "Pendaftaran Berhasil" notification is present, call showPendingNotificationToRole1()
        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        SharedPreferenceManager.SharedPreferenceHelper sharedPreferenceHelper = sharedPreferenceManager.new SharedPreferenceHelper(this);

        int adminRoleId = 1; // Assuming role ID 1 represents admin
        int mahasiswaId = 0; // Set nilai mahasiswaId ke 0 atau nilai default yang sesuai

        List<NotificationModel> notificationList = sharedPreferenceHelper.getNotifications(adminRoleId, mahasiswaId);

        // Check if there are any new "Pendaftaran Berhasil" notifications
        boolean newNotificationFound = false;
        for (NotificationModel notification : notificationList) {
            if (notification.getTitle().equals("Pendaftaran Berhasil")) {
                newNotificationFound = true;
                break;
            }
        }

        // Display the new notification if found
        if (newNotificationFound) {
            //showPendingNotificationToRole1();
        }
    }
}