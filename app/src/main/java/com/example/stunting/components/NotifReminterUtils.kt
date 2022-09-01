package com.example.stunting.components

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.stunting.data.db.model.reminder.DataReminder
import com.example.stunting.service.workmanager.NotifReminderManager
import java.util.*
import java.util.concurrent.TimeUnit

fun setupWorkManagerReminder(data: List<DataReminder>, context: Context) {
    val workManager: WorkManager = WorkManager.getInstance(context)
    workManager.cancelAllWork()
    data.forEach {
        if (it.status) {
            val dueDate = Calendar.getInstance()
            val currentDate = Calendar.getInstance()
            dueDate.set(Calendar.HOUR_OF_DAY, it.clock.split(":")[0].toInt())
            dueDate.set(Calendar.MINUTE, it.clock.split(":")[1].toInt())
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val data = Data.Builder()
            data.putString("note", it.note)
            data.putString("id_reminder", it.id_reminder)

            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            val dailyWorkRequest = OneTimeWorkRequest.Builder(NotifReminderManager::class.java)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag(it.id_reminder)
                .setInputData(data.build())
                .build()
            workManager.enqueueUniqueWork(
                it.id_reminder,
                ExistingWorkPolicy.REPLACE,
                dailyWorkRequest
            )
        }
    }
}