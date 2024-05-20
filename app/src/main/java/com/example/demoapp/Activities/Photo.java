package com.example.demoapp.Activities;

public class Photo {
    private int resourceID;
    private String url;

    public Photo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
