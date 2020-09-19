package com.example.heroesmusic.controller;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.utils.MediaPlayerGlobal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;

public class PlayMusicFragment extends Fragment {

    private static final String ARGS_POSITION = "com.example.heroesMusic.utils_position";
    private static final String ARGS_NAME = "com.example.heroesMusic.utils_singer";
    private ImageView mMusicCover, mPlayImage, mPreviousImage, mBackgroundImage;
    private ImageView mNextImage, mRepeatImage, mRandomImage;
    private TextView mMusicName, mArtistName, mCurrentTime, mTotalTime;
    private SeekBar mSeekBar;
    private Music mMusic;
    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private List<Music> mMusicList;
    private List<Music> mShuffleList;
    private boolean mRepeatBool, mRandomBool;
    private Handler mHandler;
    private double startTime;
    private String mName;

    public PlayMusicFragment() {
    }

    public static PlayMusicFragment newInstance(int position, String name) {
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION , position);
        args.putString(ARGS_NAME, name);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPosition = getArguments().getInt(ARGS_POSITION);
        mName = getArguments().getString(ARGS_NAME);
        mMusicList = MusicLab.getInstance(getActivity()).getMusicWithName(mName);
        mMusic = mMusicList.get(mPosition);
        if (mMediaPlayer == null)
            ((MediaPlayerGlobal) getActivity().getApplication()).setMediaPlayer(new MediaPlayer());
        mMediaPlayer = ((MediaPlayerGlobal) getActivity().getApplication()).getMediaPlayer();
        mShuffleList = new ArrayList<>(mMusicList);
        startTime = 0;
        mHandler = new Handler();
        mRepeatBool = false;
        mRandomBool = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music , container , false);
        findViews(view);
        setAttributesOfMusic();
        listenerOfButtons();
        return view;
    }

    private void setAttributesOfMusic() {
        startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_pause_icon);
        Bitmap bitmap = MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic);
        mMusicCover.setImageBitmap(bitmap);
        Blurry.with(getActivity()).from(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic)).into(mBackgroundImage);
        completionListener();
        handelSeekBar();
    }

    private void listenerOfButtons() {
        mPlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        mNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }
        });

        mPreviousImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMusic();
            }
        });

        mRepeatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepeatBool = !mRepeatBool;
                if (mRepeatBool)
                    mRepeatImage.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                else
                    mRepeatImage.setColorFilter(getContext().getResources().getColor(R.color.primaryTextColor));
            }
        });

        mRandomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRandomBool = ! mRandomBool;
                if (mRandomBool)
                    mRandomImage.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                else
                    mRandomImage.setColorFilter(getContext().getResources().getColor(R.color.primaryTextColor));
            }
        });
    }

    private void completionListener() {
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mRepeatBool)
                    repeatMusic();
                else
                    nextMusic();
            }
        });
    }

    private void previousMusic() {
        if (!mRandomBool) {
            mPosition -= 1;
            mPosition = mPosition < 0 ? mMusicList.size()-1 : mPosition;
            mMusic = mMusicList.get(mPosition);
        }
        else {
            Collections.shuffle(mShuffleList , new Random());
            mMusic = mShuffleList.get(mPosition);
        }
        ((MediaPlayerGlobal) getActivity().getApplication()).setMediaPlayer(new MediaPlayer());
        mMediaPlayer = ((MediaPlayerGlobal) getActivity().getApplication()).getMediaPlayer();
        setAttributesOfMusic();
    }

    private void nextMusic() {
        if (!mRandomBool) {
            mPosition += 1;
            mPosition = mPosition >= mMusicList.size() ? 0 : mPosition;
            mMusic = mMusicList.get(mPosition);
        }
        else {
            Collections.shuffle(mShuffleList , new Random());
            mMusic = mShuffleList.get(mPosition);
        }
        ((MediaPlayerGlobal) getActivity().getApplication()).setMediaPlayer(new MediaPlayer());
        mMediaPlayer = ((MediaPlayerGlobal) getActivity().getApplication()).getMediaPlayer();
        setAttributesOfMusic();
    }

    private void repeatMusic(){
        ((MediaPlayerGlobal) getActivity().getApplication()).setMediaPlayer(new MediaPlayer());
        mMediaPlayer = ((MediaPlayerGlobal) getActivity().getApplication()).getMediaPlayer();
        setAttributesOfMusic();
    }

    private void handelSeekBar() {
        mSeekBar.setMax(mMediaPlayer.getDuration());
        String totTime = createTimerLabel(mMediaPlayer.getDuration());
        mTotalTime.setText(totTime);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
      mHandler.postDelayed(UpdateSongTime , 100);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mMediaPlayer.getCurrentPosition();
            mCurrentTime.setText(createTimerLabel((int) startTime));
            mSeekBar.setProgress((int) startTime);
            mHandler.postDelayed(this, 100);
        }
    };

    public String createTimerLabel(int duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":" ;
        if (sec < 10) timerLabel += "0";
        timerLabel += sec;
        return timerLabel;
    }

    public void startMusic(Music music){
        mMediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        Uri uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mMusic.getMusicId());
        try {
            mMediaPlayer.setDataSource(getActivity(), uri);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void pause(){
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlayImage.setImageResource(R.drawable.ic_play_icon);
        }
        else {
            mMediaPlayer.start();
            mPlayImage.setImageResource(R.drawable.ic_pause_icon);
        }
    }

    private void findViews(View view) {
        mMusicCover = view.findViewById(R.id.play_cover);
        mPlayImage = view.findViewById(R.id.play_play);
        mPreviousImage = view.findViewById(R.id.play_previous);
        mNextImage = view.findViewById(R.id.play_next);
        mRepeatImage = view.findViewById(R.id.play_repeat);
        mRandomImage = view.findViewById(R.id.play_random);
        mMusicName = view.findViewById(R.id.play_music_name);
        mArtistName = view.findViewById(R.id.play_artist_name);
        mSeekBar = view.findViewById(R.id.play_seekBar);
        mCurrentTime = view.findViewById(R.id.play_start_time);
        mTotalTime = view.findViewById(R.id.play_end_time);
        mBackgroundImage = view.findViewById(R.id.background_image);
    }
}
