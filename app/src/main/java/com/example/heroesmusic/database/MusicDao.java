package com.example.heroesmusic.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.heroesmusic.model.Music;

import java.util.List;

@Dao
public interface MusicDao {
    @Query("SELECT * FROM music")
    List<Music> getAll();

    @Query("SELECT * FROM music WHERE mMusicId IN (:userIds)")
    List<Music> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM music WHERE `music name` LIKE :musicName ")
    Music findByName(String musicName);

    @Insert
    void insertAll(Music... Musics);

    @Delete
    void delete(Music music);
}
