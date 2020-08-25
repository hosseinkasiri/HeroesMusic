package com.example.heroesmusic.utils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.MediaPlayerLab;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;

public class PlayMusicFragment extends Fragment {

    private static final String ARGS_POSITION = "com.example.heroesMusic.utils_position";
    private static final String ARGS_SINGER = "com.example.heroesMusic.utils_singer";
    private ImageView mMusicCover, mPlayImage, mPreviousImage, mBackgroundImage;
    private ImageView mNextImage, mRepeatImage, mRandomImage;
    private TextView mMusicName, mArtistName, mCurrentTime, mTotalTime;
    private SeekBar mSeekBar;
    private Music mMusic;
    private String mSingerName;
    private int mPosition;
    private List<Music> mMusicList;
    private List<Music> mShuffleList;
    private boolean mRepeatBool, mRandomBool;

    public static PlayMusicFragment newInstance(int position , String singerName) {
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION , position);
        args.putString(ARGS_SINGER , singerName);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mPosition = getArguments().getInt(ARGS_POSITION);
        mSingerName = getArguments().getString(ARGS_SINGER);
        mMusicList = MusicLab.getInstance(getActivity()).getMusic(mSingerName);
        mMusic = mMusicList.get(mPosition);
        mShuffleList = new ArrayList<>(mMusicList);
        mRepeatBool = false;
        mRandomBool = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music , container , false);
        findViews(view);
        MediaPlayerLab.getInstance(getActivity()).startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        Blurry.with(getActivity()).from(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic)).into(mBackgroundImage);
        listenerOfButtons();
        //completionListener();
        handelSeekBar();


        return view;
    }

    private void listenerOfButtons() {
        mPlayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerLab.getInstance(getActivity()).pause();
                if (MediaPlayerLab.getInstance(getActivity()).isPlaying())
                    mPlayImage.setImageResource(R.drawable.ic_stop_icon);
                else
                    mPlayImage.setImageResource(R.drawable.ic_play_icon);
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
        if (mRepeatBool)
            repeatMusic();
        else
            nextMusic();
    }

    private void previousMusic() {
        if (!mRandomBool) {
            mPosition -= 1;
            mMusic = mPosition >= 0 ? mMusicList.get(mPosition) : mMusicList.get(mMusicList.size() - 1);
        }
        else {
            Collections.shuffle(mShuffleList , new Random());
            mMusic = mShuffleList.get(mPosition);
        }
        MediaPlayerLab.getInstance(getActivity()).startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_stop_icon);
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        handelSeekBar();
        completionListener();
    }

    private void nextMusic() {
        if (!mRandomBool) {
            mPosition += 1;
            mMusic = mPosition < mMusicList.size() ? mMusicList.get(mPosition) : mMusicList.get(0);
        }
        else {
            Collections.shuffle(mShuffleList , new Random());
            mMusic = mShuffleList.get(mPosition);
        }
        MediaPlayerLab.getInstance(getActivity()).startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_stop_icon);
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        handelSeekBar();
        completionListener();
    }

    private void repeatMusic(){
        MediaPlayerLab.getInstance(getActivity()).startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_stop_icon);
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        handelSeekBar();
        completionListener();
    }

    private void handelSeekBar() {
        mSeekBar.setMax(MediaPlayerLab.getInstance(getActivity()).getDuration());
        String totTime = createTimerLabel(MediaPlayerLab.getInstance(getActivity()).getDuration());
        mTotalTime.setText(totTime);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MediaPlayerLab.getInstance(getActivity()).seekTo(progress);
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (MediaPlayerLab.getInstance(getActivity()).nullOrNot()){
                    try {
                            Message message = new Message();
                            message.what = MediaPlayerLab.getInstance(getActivity()).getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (MediaPlayerLab.getInstance(getActivity()).nullOrNot()) {
                mSeekBar.setProgress(msg.what);
                mCurrentTime.setText(createTimerLabel(msg.what));
            }
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
