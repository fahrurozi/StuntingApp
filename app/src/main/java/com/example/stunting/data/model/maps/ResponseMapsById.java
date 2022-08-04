package com.example.stunting.data.model.maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMapsById {
    @SerializedName("registerd_places")
    private List<DataDbData> dataMapsById;

    public ResponseMapsById(List<DataDbData> dataMapsById) {
        this.dataMapsById = dataMapsById;
    }

    public List<DataDbData> getDataMapsById() {
        return dataMapsById;
    }

    public void setDataMapsById(List<DataDbData> dataMapsById) {
        this.dataMapsById = dataMapsById;
    }
}
