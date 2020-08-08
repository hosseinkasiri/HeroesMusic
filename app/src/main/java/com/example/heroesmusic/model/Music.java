package com.example.heroesmusic.model;

public class Music {
    private String mMusicName;
    private String mMusicPath;

    public Music(String path) {
        mMusicPath = path;
        String[] splits = mMusicPath.split("/");
        String fileName = splits[splits.length - 1];
        mMusicName = fileName.substring(0,fileName.lastIndexOf("."));
    }

    public String getMusicName() {
        return mMusicName;
    }

    public String getMusicPath() {
        return mMusicPath;
    }
}
