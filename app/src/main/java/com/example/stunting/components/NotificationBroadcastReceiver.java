package com.example.stunting.components;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.showNotificationWithFullScreenIntent(context, intent.getStringExtra("title"), intent.getStringExtra("description"));

    }



}
