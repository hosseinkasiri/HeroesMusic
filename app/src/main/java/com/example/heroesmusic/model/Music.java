package com.example.heroesmusic.model;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Music implements Parcelable {
    private String mMusicName;
    private String mMusicPath;
    private String mSinger;
    private String mAlbum;
    private Uri mMusicUri;
    private long mAlbumId;

    protected Music(Parcel in) {
        mMusicName = in.readString();
        mMusicPath = in.readString();
        mSinger = in.readString();
        mAlbum = in.readString();
        mMusicUri = in.readParcelable(Uri.class.getClassLoader());
        mAlbumId = in.readLong();
    }

    public Music() {
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

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

    public Uri getMusicUri() {
        return mMusicUri;
    }

    public void setMusicUri(Uri musicUri) {
        mMusicUri = musicUri;
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
        dest.writeString(mMusicName);
        dest.writeString(mMusicPath);
        dest.writeString(mSinger);
        dest.writeString(mAlbum);
        dest.writeParcelable(mMusicUri, flags);
        dest.writeLong(mAlbumId);
    }
}
