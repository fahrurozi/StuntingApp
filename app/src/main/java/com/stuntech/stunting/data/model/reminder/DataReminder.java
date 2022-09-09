package com.stuntech.stunting.data.model.reminder;

import com.google.gson.annotations.SerializedName;

public class DataReminder {
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private int user;
    @SerializedName("clock")
    private String clock;
    @SerializedName("repeat_each")
    private String repeat_each;
    @SerializedName("on")
    private boolean on;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getRepeat_each() {
        return repeat_each;
    }

    public void setRepeat_each(String repeat_each) {
        this.repeat_each = repeat_each;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
