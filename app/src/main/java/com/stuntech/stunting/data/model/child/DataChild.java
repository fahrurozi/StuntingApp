package com.stuntech.stunting.data.model.child;

import com.google.gson.annotations.SerializedName;

public class DataChild {
    private String week_name;
    private String week_title;
    private Integer week_count;
    @SerializedName("id")
    private Integer id;
    @SerializedName("user")
    private Integer user;
    @SerializedName("week")
    private Integer week;
    @SerializedName("height")
    private Float height;
    @SerializedName("weight")
    private Float weight;
    @SerializedName("age_day")
    private Integer age_day;
    @SerializedName("exclusive_asi")
    private Boolean exclusive_asi;
    @SerializedName("disease_history")
    private Boolean disease_history;
    @SerializedName("immunization_history")
    private String immunization_history;
    @SerializedName("children")
    private Integer children;
    @SerializedName("z_score")
    private Float z_score;
    @SerializedName("growth_level")
    private Integer growth_level;

    public DataChild(String week_name, String week_title, Integer week_count, Integer child_id) {
        this.week_name = week_name;
        this.week_title = week_title;
        this.week_count = week_count;
        this.children = child_id;
    }



    public void setDataChildServer(DataChild dataChild) {
        this.id = dataChild.id;
        this.user = dataChild.user;
        this.week = dataChild.week;
        this.height = dataChild.height;
        this.weight = dataChild.weight;
        this.age_day = dataChild.age_day;
        this.exclusive_asi = dataChild.exclusive_asi;
        this.disease_history = dataChild.disease_history;
        this.immunization_history = dataChild.immunization_history;
        this.children = dataChild.children;
        this.z_score = dataChild.z_score;
        this.growth_level = dataChild.growth_level;
    }

    public String getWeek_title() {
        return week_title;
    }

    public void setWeek_title(String week_title) {
        this.week_title = week_title;
    }

    public Integer getWeek_count() {
        return week_count;
    }

    public void setWeek_count(Integer week_count) {
        this.week_count = week_count;
    }

    public String getWeek_name() {
        return week_name;
    }

    public void setWeek_name(String week_name) {
        this.week_name = week_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getAge_day() {
        return age_day;
    }

    public void setAge_day(Integer age_day) {
        this.age_day = age_day;
    }

    public Boolean getExclusive_asi() {
        return exclusive_asi;
    }

    public void setExclusive_asi(Boolean exclusive_asi) {
        this.exclusive_asi = exclusive_asi;
    }

    public Boolean getDisease_history() {
        return disease_history;
    }

    public void setDisease_history(Boolean disease_history) {
        this.disease_history = disease_history;
    }

    public String getImmunization_history() {
        return immunization_history;
    }

    public void setImmunization_history(String immunization_history) {
        this.immunization_history = immunization_history;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Float getZ_score() {
        return z_score;
    }

    public void setZ_score(Float z_score) {
        this.z_score = z_score;
    }

    public Integer getGrowth_level() {
        return growth_level;
    }

    public void setGrowth_level(Integer growth_level) {
        this.growth_level = growth_level;
    }

    @Override
    public String toString() {
        return "DataChild{" +
                "week_name='" + week_name + '\'' +
                ", week_title='" + week_title + '\'' +
                ", week_count=" + week_count +
                ", id=" + id +
                ", user=" + user +
                ", week=" + week +
                ", height=" + height +
                ", weight=" + weight +
                ", age_day=" + age_day +
                ", exclusive_asi=" + exclusive_asi +
                ", disease_history=" + disease_history +
                ", immunization_history='" + immunization_history + '\'' +
                ", children=" + children +
                ", z_score=" + z_score +
                ", growth_level=" + growth_level +
                '}';
    }
}
