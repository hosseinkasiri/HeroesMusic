package com.example.heroesmusic.model;

public class Music {
    private String mMusicName;
    private String mMusicPath;
    private String mSinger;
    private String mAlbum;

    public String getMusicName() {
        return mMusicName;
    }

    public String getMusicPath() {
        return mMusicPath;
    }

    public String getSinger() {
        return mSinger;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public void setMusicName(String musicName) {
        mMusicName = musicName;
    }

    public void setMusicPath(String musicPath) {
        mMusicPath = musicPath;
    }
}
