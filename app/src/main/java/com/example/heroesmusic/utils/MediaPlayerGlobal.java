package com.example.heroesmusic.utils;

import android.app.Application;

import android.media.MediaPlayer;

public class MediaPlayerGlobal extends Application {
    private MediaPlayer mMediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
        mMediaPlayer = mediaPlayer;
    }
}
