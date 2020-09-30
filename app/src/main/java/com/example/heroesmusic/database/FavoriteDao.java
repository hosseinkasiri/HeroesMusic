package com.example.heroesmusic.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favoritedatasource")
    List<FavoriteDataSource> getAll();

    @Query("SELECT * FROM favoritedatasource WHERE mFavoriteId = :id")
    FavoriteDataSource loadById(long id);

    @Insert
    void insertAll(FavoriteDataSource... favoriteDataSources);

    @Query("DELETE FROM favoritedatasource WHERE mFavoriteId = :id")
    void deleteById(long id);
}