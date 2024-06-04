package com.example.demoapp.Adapters;

import java.io.Serializable;

public class NotificationPersonalPromotion implements Serializable {
    private String name;
    private String resourceID;
    private String description;
    private Long favorite;

    public NotificationPersonalPromotion(
            String name, String resourceID, String description, Long favorite
    ) {
        this.name = name;
        this.resourceID = resourceID;
        this.description = description;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void setFavorite(Long favorite) {
        this.favorite = favorite;
    }

}
