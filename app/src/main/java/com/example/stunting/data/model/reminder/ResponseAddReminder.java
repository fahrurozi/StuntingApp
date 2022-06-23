package com.example.stunting.data.model.reminder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAddReminder {
    @SerializedName("reminder_saved")
    private DataReminder reminder_saved;

    public DataReminder getReminder_saved() {
        return reminder_saved;
    }

    public void setReminder_saved(DataReminder reminder_saved) {
        this.reminder_saved = reminder_saved;
    }
}
