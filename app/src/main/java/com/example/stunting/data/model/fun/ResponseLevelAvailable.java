package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLevelAvailable {

    @SerializedName("levels")
    private List<Integer> levels;

    public ResponseLevelAvailable(List<Integer> levels) {
        this.levels = levels;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }
}
