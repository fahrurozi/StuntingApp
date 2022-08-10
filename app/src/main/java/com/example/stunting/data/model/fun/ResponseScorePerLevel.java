package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// endpoint get user answers
public class ResponseScorePerLevel {

    @SerializedName("user_answers")
    private List<DataSubmitFun> userAnswers;

    @SerializedName("user_score_per_level")
    private List<DataScorePerLevel> userScorePerLevel;

    public ResponseScorePerLevel(List<DataSubmitFun> userAnswers, List<DataScorePerLevel> userScorePerLevel) {
        this.userAnswers = userAnswers;
        this.userScorePerLevel = userScorePerLevel;
    }

    public List<DataSubmitFun> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<DataSubmitFun> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public List<DataScorePerLevel> getUserScorePerLevel() {
        return userScorePerLevel;
    }

    public void setUserScorePerLevel(List<DataScorePerLevel> userScorePerLevel) {
        this.userScorePerLevel = userScorePerLevel;
    }
}
