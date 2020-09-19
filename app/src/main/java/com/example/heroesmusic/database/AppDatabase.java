package com.example.heroesmusic.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.heroesmusic.model.Music;

@Database(entities = {Music.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    public abstract MusicDao mMusicDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null)
            sInstance = Room.databaseBuilder(context, AppDatabase.class, "room_database").allowMainThreadQueries().build();
        return sInstance;
    }
}
