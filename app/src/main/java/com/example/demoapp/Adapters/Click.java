package com.example.demoapp.Adapters;

public class Click {
    private boolean isFavorite;

    public Click(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
