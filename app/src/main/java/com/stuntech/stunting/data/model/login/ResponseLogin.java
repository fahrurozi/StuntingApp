package com.stuntech.stunting.data.model.login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("profile")
    private DataProfile profile;

    @SerializedName("user")
    private DataUser user;

    public DataProfile getProfile() {
        return profile;
    }

    public void setProfile(DataProfile profile) {
        this.profile = profile;
    }

    public DataUser getUser() {
        return user;
    }

    public void setUser(DataUser user) {
        this.user = user;
    }
}
