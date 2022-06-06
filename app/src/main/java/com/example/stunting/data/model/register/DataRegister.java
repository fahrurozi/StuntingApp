package com.example.stunting.data.model.register;

import com.google.gson.annotations.SerializedName;

public class DataRegister {
    @SerializedName("id")
    private Integer id;

    @SerializedName("authentication")
    private String authentication;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("profile_file")
    private String profile_file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_file() {
        return profile_file;
    }

    public void setProfile_file(String profile_file) {
        this.profile_file = profile_file;
    }
}
