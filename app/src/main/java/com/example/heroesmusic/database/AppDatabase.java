package com.example.heroesmusic.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.heroesmusic.model.Lyric;
import com.example.heroesmusic.utils.Converters;
import com.example.heroesmusic.utils.IntegerConverters;

@Database(entities = {FavoriteDataSource.class, Lyric.class}, version = 2)
@TypeConverters({Converters.class, IntegerConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    public abstract FavoriteDao favoriteDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null)
            sInstance = Room.databaseBuilder(context, AppDatabase.class, "room_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return sInstance;
    }
}