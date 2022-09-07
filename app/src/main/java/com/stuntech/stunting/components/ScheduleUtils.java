package com.stuntech.stunting.components;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.TimeUnit;

public class ScheduleUtils {
    Context context;

    public ScheduleUtils(Context context){
        this.context = context;
    }

    public void scheduleNotification(long seconds, String title, String description){
        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        long timeInMills = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMills, getReceiver(title, description));
    }

    public PendingIntent getReceiver(String title, String description){
        Intent targetIntent = new Intent(context, NotificationBroadcastReceiver.class);
        targetIntent.putExtra("title", title);
        targetIntent.putExtra("description", description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.context,
                1,
                targetIntent,
                PendingIntent.FLAG_IMMUTABLE
        );
        return pendingIntent;
    }
}
