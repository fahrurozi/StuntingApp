package com.example.stunting.ui.reminderv2.add

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stunting.data.db.model.reminder.DataReminder
import com.example.stunting.databinding.ActivityReminderV2AddBinding
import com.example.stunting.ui.reminderv2.ReminderV2ViewModel
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import java.util.*

class ReminderV2AddActivity : AppCompatActivity() {
    private val binding: ActivityReminderV2AddBinding by viewBinding()
    private val viewModel: ReminderV2ViewModel = ReminderV2ViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
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
                val time = "$hour:$minute"
                val date = Calendar.getInstance().time.time
                viewModel.addReminder(
                    DataReminder(
                        date.toString(),
                        time,
                        etNotes.text.toString(),
                        checkMon.isChecked,
                        checkTue.isChecked,
                        checkWed.isChecked,
                        checkThu.isChecked,
                        checkFri.isChecked,
                        checkSat.isChecked,
                        checkSun.isChecked,
                        true
                    )
                )
                finish()
            }
        }
    }
}