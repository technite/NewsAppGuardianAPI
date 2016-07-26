package com.example.android.newsapp;

public class Article {
    private String mPublicationDate;
    private String mTitle;
    private String mUrl;
    private String mContributors;

    public Article(String mPublicationDate, String mTitle, String mUrl, String mContributors) {
        this.mPublicationDate = mPublicationDate;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
        this.mContributors = mContributors;
    }

    public String getmContributors() {
        return mContributors;
    }

    public String getmPublicationDate() {
        return mPublicationDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmUrl() {return mUrl;}

    public boolean hasContributors() {return !(mContributors.isEmpty());}

}
