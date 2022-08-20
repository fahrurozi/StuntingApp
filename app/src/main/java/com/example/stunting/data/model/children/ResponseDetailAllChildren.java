package com.example.stunting.data.model.children;

import com.example.stunting.data.model.child.DataChild;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailAllChildren {
    @SerializedName("last_child_trace")
    private DataChild dataChildTrace;

    @SerializedName("children")
    private DataChildren dataChildren;

    public ResponseDetailAllChildren(DataChildren dataChildren, DataChild dataChild) {
        this.dataChildren = dataChildren;
        this.dataChildTrace = dataChild;
    }

    public DataChildren getDataChildren() {
        return dataChildren;
    }

    public void setDataChildren(DataChildren dataChildren) {
        this.dataChildren = dataChildren;
    }

    public DataChild getDataChildTrace() {
        return dataChildTrace;
    }

    public void setDataChildTrace(DataChild dataChild) {
        this.dataChildTrace = dataChild;
    }
}
