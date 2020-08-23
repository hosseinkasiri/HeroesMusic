package com.example.heroesmusic.model;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.heroesmusic.R;

import java.io.IOException;

public class MediaPlayerLab {

    private static MediaPlayerLab mInstance;
    private MediaPlayer mMediaPlayer;
    private Context mContext;

    private MediaPlayerLab(Context context) {
        mContext = context;
    }

    public static MediaPlayerLab getInstance(Context context) {
        if (mInstance == null)
            return mInstance = new MediaPlayerLab(context);
        return mInstance;
    }

    public void startMusic(Music music){
        releaseMusic();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        Uri uri = music.getMusicUri();
        try {
            mMediaPlayer.setDataSource(mContext, uri);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void releaseMusic(){
        if (mMediaPlayer != null)
            mMediaPlayer.release();
    }

    public void pause(){
        if(mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        else
            mMediaPlayer.start();
    }

    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public void seekTo(int progress){
        mMediaPlayer.seekTo(progress);
    }

    public boolean nullOrNot(){
        return mMediaPlayer != null;
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }
}
