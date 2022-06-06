package com.example.stunting.data.model.maps;

import com.google.gson.annotations.SerializedName;

public class DataPlaceDetail {
    @SerializedName("result")
    private DataResult result;

    public DataResult getResult() {
        return result;
    }

    public void setResult(DataResult result) {
        this.result = result;
    }
}
