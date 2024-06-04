package com.example.demoapp.Models.entity;

public class Thumbnail {
    private int resourceID;
    private String url;

    public Thumbnail(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
