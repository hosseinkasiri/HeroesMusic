package com.example.heroesmusic.model;

import android.graphics.Bitmap;

public class Singer {
    private String mSingerName;
    private String mPath;
    private Bitmap mSingerAlbumBitmap;

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

    public Bitmap getSingerAlbumBitmap() {
        return mSingerAlbumBitmap;
    }

    public void setSingerAlbumBitmap(Bitmap singerAlbumBitmap) {
        mSingerAlbumBitmap = singerAlbumBitmap;
    }
}
