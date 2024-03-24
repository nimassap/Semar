package com.example.semar5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the incoming message as a RemoteMessage
        RemoteMessage remoteMessage = intent.getParcelableExtra("data");

        if (remoteMessage != null) {
            // Handle the received message as needed
            if (remoteMessage.getNotification() != null) {
                // Handle the notification message
                String title = remoteMessage.getNotification().getTitle();
                String body = remoteMessage.getNotification().getBody();

                // Display a notification using NotificationCompat or other methods
                // ...
            }

            // Handle the data payload if present
            if (remoteMessage.getData().size() > 0) {
                // Handle the data payload
                // ...
            }
        }
    }
}