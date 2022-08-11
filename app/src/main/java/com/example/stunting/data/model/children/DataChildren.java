package com.example.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

public class DataChildren {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("born_date")
    private String bornDate;

    @SerializedName("gender")
    private Integer gender;

    @SerializedName("active")
    private Boolean status;

    @SerializedName("parent")
    private Integer parent;

    public DataChildren(Integer id, String name, String bornDate, Integer gender, Boolean status, Integer parent) {
        this.id = id;
        this.name = name;
        this.bornDate = bornDate;
        this.gender = gender;
        this.status = status;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
