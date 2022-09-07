package com.stuntech.stunting.components;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.stuntech.stunting.R;

import com.stuntech.stunting.ui.MainActivity;

public class NotificationUtils {
    public static NotificationManager notificationManager;
    public static String CHANNEL_ID = "STUNTECH" + "notification-channel";

    public static void showNotificationWithFullScreenIntent(Context context, String title, String description){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.stunting_trace)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent, true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationUtils.notificationManager = notificationManager;
        buildChannel(context);
        Notification notification = builder.build();
        notificationManager.notify(0, notification);

    }

    public static void buildChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Stuntech Notification Channel Name";
            String descriptionText = "Stuntech Notification Channel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NotificationUtils.CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);
            NotificationUtils.notificationManager.createNotificationChannel(channel);
        }
    }
}
