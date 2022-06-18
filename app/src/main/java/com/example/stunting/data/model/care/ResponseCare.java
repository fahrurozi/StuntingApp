package com.example.stunting.data.model.care;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCare {
    @SerializedName("articles")
    private List<DataCare> articles;

    public List<DataCare> getArticles() {
        return articles;
    }

    public void setArticles(List<DataCare> articles) {
        this.articles = articles;
    }
}
