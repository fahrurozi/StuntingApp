package com.example.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseChildren {

    @SerializedName("childrens")
    private List<DataChildren> childrens;

    public ResponseChildren(List<DataChildren> childrens) {
        this.childrens = childrens;
    }

    public List<DataChildren> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<DataChildren> childrens) {
        this.childrens = childrens;
    }


}
