package com.example.heroesmusic.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Music implements Parcelable {
    @PrimaryKey
    private Long mMusicId;
    @ColumnInfo(name = "music name")
    private String mMusicName;
    @ColumnInfo(name = "music path")
    private String mMusicPath;
    @ColumnInfo(name = "singer name")
    private String mSinger;
    @ColumnInfo(name = "album name")
    private String mAlbum;
    @ColumnInfo(name = "music album id")
    private long mAlbumId;

    public Music() {
    }

    protected Music(Parcel in) {
        mMusicName = in.readString();
        mMusicPath = in.readString();
        mSinger = in.readString();
        mAlbum = in.readString();
        mAlbumId = in.readLong();
        mMusicId = in.readLong();
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

    public long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(long albumId) {
        mAlbumId = albumId;
    }

    public Long getMusicId() {
        return mMusicId;
    }

    public void setMusicId(Long musicId) {
        mMusicId = musicId;
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
        dest.writeLong(mAlbumId);
        dest.writeLong(mMusicId);
    }
}
