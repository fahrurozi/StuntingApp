package com.stuntech.stunting.service.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.stuntech.stunting.R
import com.stuntech.stunting.components.setupWorkManagerReminder
import com.stuntech.stunting.data.db.RoomDB
import com.stuntech.stunting.data.db.model.reminder.DataReminder
import com.stuntech.stunting.ui.MainActivity
import kotlinx.coroutines.GlobalScope
import java.util.*

class NotifReminderManager(val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    private val roomDB: RoomDB = RoomDB.getInstance(context)

    override fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
        val title = "Stunting Reminder"
        val subtitle = inputData.getString("note")
        val id_reminder = inputData.getString("id_reminder")
        GlobalScope.run {
            val alarm = roomDB.reminderDao().getDataID(id_reminder.toString())
            val alarmAll = roomDB.reminderDao().getData()
            if (alarmIsToday(alarm)) sendNotification(id, title, subtitle.toString())
            setupWorkManagerReminder(alarmAll, context)
        }

        return Result.success()
    }

    private fun sendNotification(id: Int, title: String, subtitle: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(subtitle))
            .setDefaults(NotificationCompat.DEFAULT_ALL).setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notification.priority = NotificationCompat.PRIORITY_MAX

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    NOTIFICATION_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }


    private fun alarmIsToday(dataAlarm: DataReminder): Boolean {
        val currentDate = Calendar.getInstance()
        when (currentDate.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> {
                return dataAlarm.sunday
            }
            Calendar.MONDAY -> {
                return dataAlarm.monday
            }

            Calendar.TUESDAY -> {
                return dataAlarm.tuesday
            }

            Calendar.WEDNESDAY -> {
                return dataAlarm.wednesday
            }

            Calendar.THURSDAY -> {
                return dataAlarm.thursday
            }

            Calendar.FRIDAY -> {
                return dataAlarm.friday
            }

            Calendar.SATURDAY -> {
                return dataAlarm.saturday
            }
        }
        return false
    }

    companion object {
        const val NOTIFICATION_ID = "stunting_notification_reminder"
        const val NOTIFICATION_NAME = "Stunting"
        const val NOTIFICATION_CHANNEL = "stunting_reminder"
        const val NOTIFICATION_WORK = "reminder_notification_work"
    }

}