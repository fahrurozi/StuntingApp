package com.stuntech.stunting.data.model.login;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
