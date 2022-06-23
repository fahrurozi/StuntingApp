package com.example.stunting.data.model.reminder;

import com.example.stunting.data.model.care.DataCare;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseReminder {
    @SerializedName("all_user_reminders")
    private List<DataReminder> reminders;

    public List<DataReminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<DataReminder> reminders) {
        this.reminders = reminders;
    }
}
