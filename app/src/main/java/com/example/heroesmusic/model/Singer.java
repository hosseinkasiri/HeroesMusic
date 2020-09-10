package com.example.heroesmusic.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Singer implements Parcelable {
    private String mSingerName;
    private String mPath;
    private long mAlbumId;

    public Singer() {
    }

    protected Singer(Parcel in) {
        mSingerName = in.readString();
        mPath = in.readString();
        mAlbumId = in.readLong();
    }

    public static final Creator<Singer> CREATOR = new Creator<Singer>() {
        @Override
        public Singer createFromParcel(Parcel in) {
            return new Singer(in);
        }

        @Override
        public Singer[] newArray(int size) {
            return new Singer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSingerName);
        dest.writeString(mPath);
        dest.writeLong(mAlbumId);
    }
}
