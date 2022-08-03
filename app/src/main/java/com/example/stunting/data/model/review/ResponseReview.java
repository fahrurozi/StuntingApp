package com.example.stunting.data.model.review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseReview {
    @SerializedName("reviews")
    private List<DataReview> reviews;

    public List<DataReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<DataReview> reviews) {
        this.reviews = reviews;
    }
}
