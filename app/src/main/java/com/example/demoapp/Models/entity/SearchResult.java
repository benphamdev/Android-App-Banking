package com.example.demoapp.Models.entity;

public class SearchResult {
    private final String title;
    private final String description;

    public SearchResult(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

