package com.example.stunting.ui.reminderv2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunting.data.db.model.reminder.DataReminder
import com.example.stunting.root.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderV2ViewModel : ViewModel() {
    private lateinit var resultReminder: LiveData<List<DataReminder>>

    init {
        subscribeReminder()
    }


    fun listenReminder(): LiveData<List<DataReminder>> {
        return resultReminder
    }

    fun addReminder(dataReminder: DataReminder) {
        viewModelScope.launch(Dispatchers.IO) {
            App.roomDB.reminderDao().insertData(dataReminder)
        }
    }

    fun deleteReminder(dataReminder: DataReminder) {
        viewModelScope.launch(Dispatchers.IO) {
            App.roomDB.reminderDao().deleteData(dataReminder)
        }
    }

    fun updateReminder(dataReminder: DataReminder) {
        viewModelScope.launch(Dispatchers.IO) {
            App.roomDB.reminderDao().update(dataReminder)
        }
    }

    private fun subscribeReminder() {
        resultReminder = App.roomDB.reminderDao().getDataLive()
    }
}