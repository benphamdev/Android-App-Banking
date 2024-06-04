package com.example.demoapp.Models.Dto.Response;

import java.util.List;

import retrofit2.http.Tag;

public class PostResponse {
    Integer id;
    String name;
    String content;
    Long likeCount;
    Long viewCount;
    String thumbnail;
    List<Tag> tags;

    public PostResponse(
            Integer id, String name, String content, Long likeCount, Long viewCount,
            String thumbnail, List<Tag> tags
    ) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
