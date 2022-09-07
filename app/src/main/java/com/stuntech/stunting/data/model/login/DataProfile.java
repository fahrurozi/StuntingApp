package com.stuntech.stunting.data.model.login;

import com.google.gson.annotations.SerializedName;

public class DataProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("b64_profile_img")
    private String b64_profile_img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getB64_profile_img() {
        return b64_profile_img;
    }

    public void setB64_profile_img(String b64_profile_img) {
        this.b64_profile_img = b64_profile_img;
    }
}
