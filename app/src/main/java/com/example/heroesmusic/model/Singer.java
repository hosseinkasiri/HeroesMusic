package com.example.heroesmusic.model;

import android.graphics.Bitmap;

public class Singer {
    private String mSingerName;
    private String mPath;
    private long mAlbumId;

    public String getSingerName() {
        return mSingerName;
    }

    public void setSingerName(String singerName) {
        mSingerName = singerName;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(long albumId) {
        mAlbumId = albumId;
    }
}
