package com.stuntech.stunting.ui.reminderv2.add

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stuntech.stunting.data.db.model.reminder.DataReminder
import com.stuntech.stunting.databinding.ActivityReminderV2AddBinding
import com.stuntech.stunting.ui.reminderv2.ReminderV2ViewModel
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.toast
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
                var isSelected = false
                listOf(
                    checkMon,
                    checkTue,
                    checkWed,
                    checkThu,
                    checkFri,
                    checkSat,
                    checkSun
                ).forEach {
                    if (it.isSelected) {
                        isSelected = true
                    }
                }

                if (!isSelected || etNotes.text.toString().isEmpty()) {
                    toast("Mohon isi semua filed yang ada")
                } else {
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
    }
}