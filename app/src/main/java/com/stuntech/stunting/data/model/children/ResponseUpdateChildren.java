package com.stuntech.stunting.data.model.children;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateChildren {
    @SerializedName("updated_child")
    private DataChildren dataChildren;

    public ResponseUpdateChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }

    public DataChildren getDataChildren() {
        return dataChildren;
    }

    public void setDataChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }
}
