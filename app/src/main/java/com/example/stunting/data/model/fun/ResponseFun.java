package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//endpoint get qas
public class ResponseFun {

    @SerializedName("qas")
    private List<DataFun> qas;

    public ResponseFun(List<DataFun> qas) {
        this.qas = qas;
    }

    public List<DataFun> getQas() {
        return qas;
    }

    public void setQas(List<DataFun> qas) {
        this.qas = qas;
    }
}
