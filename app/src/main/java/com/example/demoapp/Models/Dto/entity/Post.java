package com.example.demoapp.Models.Dto.entity;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.List;

public class Post {
    String name;
    String content;
    Long likeCount;
    Long viewCount;

    List<Tag> tags = new ArrayList<>();

    Photo photo;

    public Post(
            String name, String content, Long likeCount, Long viewCount, List<Tag> tags, Photo photo
    ) {
        this.name = name;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.tags = tags;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
