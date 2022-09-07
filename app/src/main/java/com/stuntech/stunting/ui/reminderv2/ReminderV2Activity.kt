package com.stuntech.stunting.ui.reminderv2

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.stuntech.stunting.R
import com.stuntech.stunting.components.setupWorkManagerReminder
import com.stuntech.stunting.databinding.ActivityReminderV2Binding
import com.stuntech.stunting.ui.reminderv2.add.ReminderV2AddActivity
import com.stuntech.stunting.ui.reminderv2.edit.ReminderV2EditActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.startActivity

class ReminderV2Activity : AppCompatActivity() {
    private val binding: ActivityReminderV2Binding by viewBinding()
    private val viewModel: ReminderV2ViewModel = ReminderV2ViewModel()
    private val adapter: ReminderV2Adapter = ReminderV2Adapter({
        viewModel.updateReminder(it)
    }, {
        AlertDialog.Builder(this)
            .setTitle("Perhatian")
            .setMessage("Pilih Operasi yang Akan Dilakukan?")
            .setIcon(R.mipmap.ic_launcher_round)
            .setCancelable(true)
            .setPositiveButton("hapus") { dialog, _ ->
                dialog.dismiss()
                viewModel.deleteReminder(it)
            }
            .setNegativeButton("Ubah") { dialog, _ ->
                dialog.dismiss()
                startActivity(ReminderV2EditActivity::class.java) { intent ->
                    intent.putExtra("data", it)
                }
            }.show()

    })

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