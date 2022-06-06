package com.example.stunting.data.model.maps;

import com.google.gson.annotations.SerializedName;

public class DataPlace {
    @SerializedName("place_detail")
    private DataPlaceDetail placeDetail;

    public DataPlaceDetail getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(DataPlaceDetail placeDetail) {
        this.placeDetail = placeDetail;
    }


}
