package com.stuntech.stunting.data.model.child;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseChild {
    @SerializedName("all_traces")
    private List<DataChild> all_traces;

    public List<DataChild> getAll_traces() {
        return all_traces;
    }

    public void setAll_traces(List<DataChild> all_traces) {
        this.all_traces = all_traces;
    }

    @Override
    public String toString() {
        return "ResponseChild{" +
                "all_traces=" + all_traces +
                '}';
    }
}
