package com.stuntech.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

public class DataScorePerLevel {

    @SerializedName("question_count_level")
    private Integer questionLevelCount;

    @SerializedName("correct_answers_count")
    private Integer CorrectAnswerCount;

    @SerializedName("level")
    private Integer level;

    public DataScorePerLevel(Integer questionLevelCount, Integer correctAnswerCount, Integer level) {
        this.questionLevelCount = questionLevelCount;
        CorrectAnswerCount = correctAnswerCount;
        this.level = level;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
