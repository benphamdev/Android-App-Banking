package com.example.demoapp.Adapters;

public class User {
    private String images;
    private int id;

    public User(String images, int id) {
        this.images = images;
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
