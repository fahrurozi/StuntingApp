package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

public class DataScorePerLevel {

    @SerializedName("question_count_level")
    private Integer questionLevelCount;

    @SerializedName("correct_answers_count")
    private Integer CorrectAnswerCount;

    public DataScorePerLevel(Integer questionLevelCount, Integer correctAnswerCount) {
        this.questionLevelCount = questionLevelCount;
        CorrectAnswerCount = correctAnswerCount;
    }

    public Integer getQuestionLevelCount() {
        return questionLevelCount;
    }

    public void setQuestionLevelCount(Integer questionLevelCount) {
        this.questionLevelCount = questionLevelCount;
    }

    public Integer getCorrectAnswerCount() {
        return CorrectAnswerCount;
    }

    public void setCorrectAnswerCount(Integer correctAnswerCount) {
        CorrectAnswerCount = correctAnswerCount;
    }
}
