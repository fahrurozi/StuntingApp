package com.example.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

public class ResponseDeleteChildren {
    @SerializedName("deleted_child")
    private DataChildren data;

    public ResponseDeleteChildren(DataChildren data) {
        this.data = data;
    }

    public DataChildren getData() {
        return data;
    }

    public void setData(DataChildren data) {
        this.data = data;
    }
}
