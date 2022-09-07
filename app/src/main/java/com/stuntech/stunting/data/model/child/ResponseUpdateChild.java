package com.stuntech.stunting.data.model.child;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUpdateChild {

    @SerializedName("updated")
    private List<DataChild> updated;

    public List<DataChild> getUpdated() {
        return updated;
    }

    public void setUpdated(List<DataChild> updated) {
        this.updated = updated;
    }
}
