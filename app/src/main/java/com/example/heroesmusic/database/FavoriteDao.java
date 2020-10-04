package com.example.heroesmusic.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heroesmusic.model.Lyric;

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

    @Query("SELECT * FROM lyric")
    List<Lyric> getAllLyrics();

    @Query("SELECT * FROM lyric WHERE mLyricId = :id")
    Lyric getLyricById(long id);

    @Insert
    void insertLyrics(Lyric... lyrics);

    @Query("DELETE FROM lyric WHERE mLyricId = :id")
    void deleteLyricById(long id);

    @Update
    void updateLyric(Lyric lyric);
}