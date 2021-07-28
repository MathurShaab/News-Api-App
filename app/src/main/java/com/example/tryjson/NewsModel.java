package com.example.tryjson;

public class NewsModel {

    String newsTitle, newsDescription, newsDate, newsImageUrl,url;

    public NewsModel(String newsTitle, String newsDescription, String newsDate, String newsImageUrl, String url) {
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.newsDate = newsDate;
        this.newsImageUrl = newsImageUrl;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

}
