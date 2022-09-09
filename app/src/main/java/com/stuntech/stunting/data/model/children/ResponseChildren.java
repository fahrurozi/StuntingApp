package com.stuntech.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseChildren {

    @SerializedName("all_childrens")
    private List<ResponseDetailAllChildren> childrens;

    public ResponseChildren(List<ResponseDetailAllChildren> childrens) {
        this.childrens = childrens;
    }

    public List<ResponseDetailAllChildren> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<ResponseDetailAllChildren> childrens) {
        this.childrens = childrens;
    }
}
