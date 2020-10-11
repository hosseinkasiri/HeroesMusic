package com.example.heroesmusic.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.heroesmusic.utils.Converters;
import com.example.heroesmusic.utils.DoubleConverters;

import java.util.List;

@Entity
public class Lyric {
    @PrimaryKey
    private Long mLyricId;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "texts_lyric")
    private List<String> mTexts;
    @TypeConverters(DoubleConverters.class)
    @ColumnInfo(name = "startTime_lyric")
    private List<Double> mStartTime;

    public Lyric() {
    }

    public Lyric(Long lyricId, List<String> texts, List<Double> startTime) {
        mLyricId = lyricId;
        mTexts = texts;
        mStartTime = startTime;
    }

    public Long getLyricId() {
        return mLyricId;
    }

    public void setLyricId(Long lyricId) {
        mLyricId = lyricId;
    }

    public List<String> getTexts() {
        return mTexts;
    }

    public void setTexts(List<String> texts) {
        mTexts = texts;
    }

    public List<Double> getStartTime() {
        return mStartTime;
    }

    public void setStartTime(List<Double> startTime) {
        mStartTime = startTime;
    }
}
