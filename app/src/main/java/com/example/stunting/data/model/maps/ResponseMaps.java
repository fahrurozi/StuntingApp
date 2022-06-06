package com.example.stunting.data.model.maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMaps {
    @SerializedName("places")
    List<DataPlace> places;

    public List<DataPlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<DataPlace> places) {
        this.places = places;
    }
}
