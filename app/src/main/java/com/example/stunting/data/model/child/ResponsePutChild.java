package com.example.stunting.data.model.child;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePutChild {
    @SerializedName("saved_traces")
    private List<DataChild> saved_traces;

    public List<DataChild> getAll_traces() {
        return saved_traces;
    }

    public void setAll_traces(List<DataChild> all_traces) {
        this.saved_traces = all_traces;
    }
}
