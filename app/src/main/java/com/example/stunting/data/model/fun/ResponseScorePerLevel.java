package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseScorePerLevel {

    @SerializedName("user_score_per_level")
    private List<ResponsePerLevel> userScorePerLevel;

    public ResponseScorePerLevel(List<ResponsePerLevel> userScorePerLevel) {
        this.userScorePerLevel = userScorePerLevel;
    }

    public List<ResponsePerLevel> getUserScorePerLevel() {
        return userScorePerLevel;
    }

    public void setUserScorePerLevel(List<ResponsePerLevel> userScorePerLevel) {
        this.userScorePerLevel = userScorePerLevel;
    }
}
