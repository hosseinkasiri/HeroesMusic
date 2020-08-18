package com.example.heroesmusic.model;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;

public class Music {
    private String mMusicName;
    private String mMusicPath;
    private String mSinger;
    private String mAlbum;
    private Bitmap mAlbumArtBitmap;
    private Uri mAlbumArtUri;
    private int mDuration;
    private MediaPlayer mMediaPlayer;
    private Uri mMusicUri;

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

    public Bitmap getAlbumArtBitmap() {
        return mAlbumArtBitmap;
    }

    public void setAlbumArtBitmap(Bitmap albumArt) {
        mAlbumArtBitmap = albumArt;
    }

    public Uri getAlbumArtUri() {
        return mAlbumArtUri;
    }

    public void setAlbumArtUri(Uri albumArtUri) {
        mAlbumArtUri = albumArtUri;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public Uri getMusicUri() {
        return mMusicUri;
    }

    public void setMusicUri(Uri musicUri) {
        mMusicUri = musicUri;
    }
}
