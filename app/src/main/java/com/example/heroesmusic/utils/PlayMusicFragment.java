package com.example.heroesmusic.utils;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayMusicFragment extends Fragment {

    private static final String ARGS_POSITION = "com.example.heroesMusic.utils_position";
    private ImageView mMusicCover , mPlayImage , mPreviousImage;
    private ImageView mNextImage,mRepeatImage , mRandomImage;
    private TextView mMusicName , mArtistName, mCurrentTime, mTotalTime;
    private SeekBar mSeekBar;
    private Music mMusic;
    private int mPosition;
    private List<Music> mMusicList;
    private List<Music> mShuffleList;
    private MediaPlayer mMediaPlayer;
    private boolean mRepeatBool , mRandomBool ;

    public static PlayMusicFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARGS_POSITION , position);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mMusicList = MusicLab.getInstance(getActivity()).getMusicList();
        mPosition = getArguments().getInt(ARGS_POSITION);
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
        startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
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
        completionListener();
        handelSeekBar();
        return view;
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
            mMusic = mPosition >= 0 ? mMusicList.get(mPosition) : mMusicList.get(mMusicList.size() - 1);
        }
        else {
            Collections.shuffle(mShuffleList , new Random());
            mMusic = mShuffleList.get(mPosition);
        }
        startMusic(mMusic);
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
        startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_stop_icon);
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        handelSeekBar();
        completionListener();
    }

    private void repeatMusic(){
        startMusic(mMusic);
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlayImage.setImageResource(R.drawable.ic_stop_icon);
        mMusicCover.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic));
        handelSeekBar();
        completionListener();
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer != null){
                    try {
                        Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
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
            mSeekBar.setProgress(msg.what);
            mCurrentTime.setText(createTimerLabel(msg.what));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

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
            mMediaPlayer.setDataSource(getActivity(), uri);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void releaseMusic(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying())
            mMediaPlayer.release();
    }

    public void pause(){
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            mPlayImage.setImageResource(R.drawable.ic_play_icon);
        } else {
            mMediaPlayer.start();
            mPlayImage.setImageResource(R.drawable.ic_stop_icon);
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
    }
}
