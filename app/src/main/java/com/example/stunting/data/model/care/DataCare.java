package com.example.stunting.data.model.care;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataCare implements Parcelable {
    @SerializedName("article_cover_file")
    private String articleCoverFile;
    @SerializedName("article_file")
    private String articleFile;
    @SerializedName("article_sub_type")
    private String articleSubType;
    @SerializedName("article_tags")
    private String articleTags;
    @SerializedName("article_type")
    private String articleType;
    @SerializedName("date")
    private String date;
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;

    protected DataCare(Parcel in) {
        articleCoverFile = in.readString();
        articleFile = in.readString();
        articleSubType = in.readString();
        articleTags = in.readString();
        articleType = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
    }

    public static final Creator<DataCare> CREATOR = new Creator<DataCare>() {
        @Override
        public DataCare createFromParcel(Parcel in) {
            return new DataCare(in);
        }

        @Override
        public DataCare[] newArray(int size) {
            return new DataCare[size];
        }
    };

    public String getArticleCoverFile() {
        return articleCoverFile;
    }

    public void setArticleCoverFile(String articleCoverFile) {
        this.articleCoverFile = articleCoverFile;
    }

    public String getArticleFile() {
        return articleFile;
    }

    public void setArticleFile(String articleFile) {
        this.articleFile = articleFile;
    }

    public String getArticleSubType() {
        return articleSubType;
    }

    @Override
    public String toString() {
        return "DataCare{" +
                "articleCoverFile='" + articleCoverFile + '\'' +
                ", articleFile='" + articleFile + '\'' +
                ", articleSubType='" + articleSubType + '\'' +
                ", articleTags='" + articleTags + '\'' +
                ", articleType='" + articleType + '\'' +
                ", date='" + date + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public void setArticleSubType(String articleSubType) {
        this.articleSubType = articleSubType;
    }

    public String getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(String articleTags) {
        this.articleTags = articleTags;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(articleCoverFile);
        parcel.writeString(articleFile);
        parcel.writeString(articleSubType);
        parcel.writeString(articleTags);
        parcel.writeString(articleType);
        parcel.writeString(date);
        parcel.writeInt(id);
        parcel.writeString(title);
    }
}
