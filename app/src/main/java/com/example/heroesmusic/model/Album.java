package com.example.heroesmusic.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {
    private String mAlbumName;
    private String mAlbumPath;
    private String mSinger;
    private long mAlbumId;

    protected Album(Parcel in) {
        mAlbumName = in.readString();
        mAlbumPath = in.readString();
        mSinger = in.readString();
        mAlbumId = in.readLong();
    }

    public Album() {
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlbumName);
        dest.writeString(mAlbumPath);
        dest.writeString(mSinger);
        dest.writeLong(mAlbumId);
    }
}
