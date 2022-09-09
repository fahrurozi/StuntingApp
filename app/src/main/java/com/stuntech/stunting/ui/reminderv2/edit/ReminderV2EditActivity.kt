package com.stuntech.stunting.ui.reminderv2.edit

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stuntech.stunting.data.db.model.reminder.DataReminder
import com.stuntech.stunting.databinding.ActivityReminderV2AddBinding
import com.stuntech.stunting.ui.reminderv2.ReminderV2ViewModel
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.binding.intent.intent
import com.oratakashi.viewbinding.core.tools.onClick

class ReminderV2EditActivity : AppCompatActivity() {
    private val binding: ActivityReminderV2AddBinding by viewBinding()
    private val viewModel: ReminderV2ViewModel = ReminderV2ViewModel()
    private val data: DataReminder by intent("data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            tvTitle.text = "Edit Reminder"

            etNotes.setText(data.note)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tpInputTime.hour = data.clock.split(":")[0].toInt()
            } else {
                tpInputTime.currentHour = data.clock.split(":")[0].toInt()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tpInputTime.minute = data.clock.split(":")[1].toInt()
            } else {
                tpInputTime.currentMinute = data.clock.split(":")[1].toInt()
            }

            listOf(checkMon, checkTue, checkWed, checkThu, checkFri, checkSat, checkSun).forEach {
                data.monday
            }

            if (data.sunday) checkSun.isSelected = true
            if (data.monday) checkMon.isSelected = true
            if (data.tuesday) checkTue.isSelected = true
            if (data.wednesday) checkWed.isSelected = true
            if (data.thursday) checkThu.isSelected = true
            if (data.friday) checkFri.isSelected = true
            if (data.saturday) checkSat.isSelected = true

            btnBack.onClick { finish() }
            fabSimpan.onClick {
                val hour: Int
                val minute: Int
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = tpInputTime.hour
                    minute = tpInputTime.minute
                } else {
                    hour = tpInputTime.currentHour
                    minute = tpInputTime.currentMinute
                }
                val fixHour = fixTime(hour.toString())
                val fixMinute = fixTime(minute.toString())
                val time = "$fixHour:$fixMinute"
                viewModel.updateReminder(
                    DataReminder(
                        data.id_reminder,
                        time,
                        etNotes.text.toString(),
                        checkMon.isSelected,
                        checkTue.isSelected,
                        checkWed.isSelected,
                        checkThu.isSelected,
                        checkFri.isSelected,
                        checkSat.isSelected,
                        checkSun.isSelected,
                        true
                    )
                )
                finish()
            }
        }
    }

    private fun fixTime(time: String): String {
        if (time.length == 1) {
            return "0$time"
        }
        return time
    }
}