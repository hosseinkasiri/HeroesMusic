package com.example.heroesmusic.model;

import android.graphics.Bitmap;

public class Album {
    private String mAlbumName;
    private String mAlbumPath;
    private String mSinger;
    private Bitmap mAlbumBitmap;

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

    public Bitmap getAlbumBitmap() {
        return mAlbumBitmap;
    }

    public void setAlbumBitmap(Bitmap albumBitmap) {
        mAlbumBitmap = albumBitmap;
    }
}
