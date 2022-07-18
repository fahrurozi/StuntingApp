package com.example.stunting.data.model.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseInfo {
    @SerializedName("articles")
    private List<DataInfo> articles;

    public List<DataInfo> getArticles() {
        return articles;
    }

    public void setArticles(List<DataInfo> articles) {
        this.articles = articles;
    }
}
