package com.example.semar5;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //private static final String TAG = "MyFirebaseMessagingServ";

    /*@Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this app's subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        //System.out.println("From: " + remoteMessage.getFrom());



        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //sendNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());

        /*Map<String, String> data = remoteMessage.getData();
        if (data != null) {
            String title = data.get("title");
            String body = data.get("body");
            // Tampilkan notifikasi kepada pengguna
            showNotification(title, body);
        }*/

        if (remoteMessage.getNotification() != null) {
            // Menampilkan notifikasi
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }


    private void showNotification(String title, String message) {
        /*NotificationManager notificationManager = getSystemService(NotificationManager.class);
        String channelId = "channel_id";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.bottom_notif)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(0, builder.build());*/

        Context context = getApplicationContext();
        String channelId = "MyChannelID"; // ID channel notifikasi

        // Membuat channel notifikasi (untuk Android Oreo dan yang lebih baru)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "My Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("My Notification Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Membuat notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logosemar1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(0, builder.build()); // Gunakan id unik
    }

    /*private void sendNotification(String from, String body) {
    }

    private void sendRegistrationToServer(String token) {
        // Implement your logic to send the token to your app server
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyFirebaseMessagingService.this, "Test", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}