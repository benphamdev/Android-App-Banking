package com.example.demoapp.Models.Dto.entity;

import java.io.Serializable;

public class Photo implements Serializable {
    String publicId;

    String url;

    public Photo(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
