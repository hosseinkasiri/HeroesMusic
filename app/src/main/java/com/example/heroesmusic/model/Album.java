package com.example.heroesmusic.model;

import android.graphics.Bitmap;

public class Album {
    private String mAlbumName;
    private String mAlbumPath;
    private String mSinger;
    private long mAlbumId;

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getAlbumPath() {
        return mAlbumPath;
    }

    public void setAlbumPath(String albumPath) {
        mAlbumPath = albumPath;
    }

    public String getSinger() {
        return mSinger;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(long albumId) {
        mAlbumId = albumId;
    }
}
