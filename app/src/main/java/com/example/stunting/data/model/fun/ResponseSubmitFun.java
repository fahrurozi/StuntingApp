package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

//endpoint submit answer
public class ResponseSubmitFun {

    @SerializedName("answer")
    private DataSubmitFun answer;

    public ResponseSubmitFun(DataSubmitFun answer) {
        this.answer = answer;
    }

    public DataSubmitFun getAnswer() {
        return answer;
    }

    public void setAnswer(DataSubmitFun answer) {
        this.answer = answer;
    }
}
