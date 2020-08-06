package com.example.heroesmusic.model;

public class Music {
    private String mName;
    private String mPath;

    public Music(String path) {
        mPath = path;
        String[] splits = mPath.split("/");
        String fileName = splits[splits.length - 1];
        mName = fileName.substring(0,fileName.lastIndexOf("."));
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }
}
