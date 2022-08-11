package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

public class DataSubmitFun {

    @SerializedName("id")
    private Integer id;

    @SerializedName("user")
    private Integer userId;

    @SerializedName("question")
    private Integer questionId;

    @SerializedName("answer")
    private Integer answerIndex;

    @SerializedName("answer_is_correct")
    private Boolean answerIsCorrect;

    public DataSubmitFun(Integer id, Integer userId, Integer questionId, Integer answerIndex, Boolean answerIsCorrect) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answerIndex = answerIndex;
        this.answerIsCorrect = answerIsCorrect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    public Boolean getAnswerIsCorrect() {
        return answerIsCorrect;
    }

    public void setAnswerIsCorrect(Boolean answerIsCorrect) {
        this.answerIsCorrect = answerIsCorrect;
    }
}
