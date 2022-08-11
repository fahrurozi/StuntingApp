package com.example.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

public class ResponseAddChildren {

    @SerializedName("saved_child")
    private DataChildren dataChildren;

    public ResponseAddChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }

    public DataChildren getDataChildren() {
        return dataChildren;
    }

    public void setDataChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }
}
