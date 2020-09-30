package com.example.heroesmusic.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteDataSource {
    @PrimaryKey
    private Long mFavoriteId;

    public FavoriteDataSource(Long favoriteId) {
        mFavoriteId = favoriteId;
    }

    public Long getFavoriteId() {
        return mFavoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        mFavoriteId = favoriteId;
    }
}
