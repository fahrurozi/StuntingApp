package com.example.stunting.ui.reminderv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stunting.components.setupWorkManagerReminder
import com.example.stunting.databinding.ActivityReminderV2Binding
import com.example.stunting.ui.reminderv2.add.ReminderV2AddActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.startActivity

class ReminderV2Activity : AppCompatActivity() {
    private val binding: ActivityReminderV2Binding by viewBinding()
    private val viewModel: ReminderV2ViewModel = ReminderV2ViewModel()
    private val adapter: ReminderV2Adapter = ReminderV2Adapter {
        viewModel.updateReminder(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initListener()
        with(binding) {
            btnBack.onClick { finish() }
            rvData.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(this@ReminderV2Activity)
            }
            fabTambah.onClick { startActivity(ReminderV2AddActivity::class.java) }
        }
    }

    private fun initListener() {
        viewModel.listenReminder().observe(this) {
            adapter.submitData(it)
            setupWorkManagerReminder(it, baseContext)
        }
    }
}