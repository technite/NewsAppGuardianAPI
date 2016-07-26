package com.example.android.newsapp;

public class Article {
    private String mPublicationDate;
    private String mTitle;
    private String mUrl;

    public Article(String mPublicationDate, String mTitle, String mUrl) {
        this.mPublicationDate = mPublicationDate;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
    }

    public String getmPublicationDate() {
        return mPublicationDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmUrl() {return mUrl;}
}
