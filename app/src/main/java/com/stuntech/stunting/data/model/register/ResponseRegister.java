package com.stuntech.stunting.data.model.register;

import com.google.gson.annotations.SerializedName;

public class ResponseRegister {
    @SerializedName("profile")
    private DataRegister profile;

    public DataRegister getProfile() {
        return profile;
    }

    public void setProfile(DataRegister profile) {
        this.profile = profile;
    }
}
