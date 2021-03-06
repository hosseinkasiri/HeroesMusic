package com.example.heroesmusic.controller;

import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Range;
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
import com.example.heroesmusic.database.AppDatabase;
import com.example.heroesmusic.database.FavoriteDataSource;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Lyric;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.utils.MediaPlayerGlobal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;

public class PlayMusicFragment extends Fragment {

    private static final String ARGS_POSITION = "com.example.heroesMusic.utils_position";
    private static final String ARGS_NAME = "com.example.heroesMusic.utils_singer";

    private ImageView mMusicCover, mPlay, mPrevious, mBackground, mShowLyric;
    private ImageView mNext, mRepeat, mRandom, mFavorite, mLyricIcon;
    private TextView mMusicName, mArtistName, mCurrentTime, mTotalTime, mLyricText;
    private SeekBar mSeekBar;

    private Music mMusic;
    private MediaPlayer mMediaPlayer;
    private int mPosition;
    private List<Music> mMusicList;
    private List<Music> mShuffleList;
    private boolean mRepeatBool, mRandomBool;
    private Handler mHandler;
    private double mCurrent;
    private String mName;
    private boolean mFavoriteBool;
    private Lyric mLyric;
    private List<Range<Double>> mRanges;

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
        mCurrent = 0;
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music , container , false);
        findViews(view);
        if (AppDatabase.getInstance(getActivity()).favoriteDao().getLyricById(mMusic.getMusicId()) != null){
            mLyric = AppDatabase.getInstance(getActivity()).favoriteDao().getLyricById(mMusic.getMusicId());
        }
        setAttributesOfMusic();
        listenerOfButtons();
        setRanges();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppDatabase.getInstance(getActivity()).favoriteDao().getLyricById(mMusic.getMusicId()) != null){
            mLyric = AppDatabase.getInstance(getActivity()).favoriteDao().getLyricById(mMusic.getMusicId());
            setRanges();
        }
        mPlay.setImageResource(mMediaPlayer.isPlaying() ? R.drawable.ic_pause_icon : R.drawable.ic_play_icon);
    }

    private void  setRanges() {
        if (mLyric == null ) {
            return;
        }
        mRanges = new ArrayList<>();
        for (int i = 0; i<mLyric.getTexts().size(); i++) {
            if (i != mLyric.getTexts().size() -1) {
                double endTime =  mLyric.getStartTime().get(i+1);
                double startTime = mLyric.getStartTime().get(i);
                Range<Double> range = new Range<>(startTime,endTime);
                mRanges.add(range);
            } else {
                Double startTime = mLyric.getStartTime().get(i);
                Range<Double> range = new Range<>(startTime,Double.POSITIVE_INFINITY);
                mRanges.add(range);
            }
        }
    }

    private void setAttributesOfMusic() {
        startMusic();
        mFavoriteBool = AppDatabase.getInstance(getActivity()).favoriteDao().loadById(mMusic.getMusicId()) != null;
        if (mFavoriteBool) mFavorite.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
        else mFavorite.setColorFilter(getContext().getResources().getColor(R.color.primaryLightColor));
        mMusicName.setText(mMusic.getMusicName());
        mArtistName.setText(mMusic.getSinger());
        mPlay.setImageResource(R.drawable.ic_pause_icon);
        mLyricText.setVisibility(View.GONE);
        Bitmap bitmap = MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic);
        mMusicCover.setImageBitmap(bitmap);
        Blurry.with(getActivity()).from(MusicLab.getInstance(getActivity()).getMusicBitmap(mMusic)).into(mBackground);
        completionListener();
        handelSeekBar();
    }

    private void listenerOfButtons() {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMusic();
            }
        });

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMusic();
            }
        });

        mRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRepeatBool = !mRepeatBool;
                if (mRepeatBool)
                    mRepeat.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                else
                    mRepeat.setColorFilter(getContext().getResources().getColor(R.color.primaryTextColor));
            }
        });

        mRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRandomBool = ! mRandomBool;
                if (mRandomBool)
                    mRandom.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                else
                    mRandom.setColorFilter(getContext().getResources().getColor(R.color.primaryTextColor));
            }
        });

        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFavoriteBool) {
                    AppDatabase.getInstance(getActivity()).favoriteDao().deleteById(mMusic.getMusicId());
                    mFavorite.setColorFilter(getContext().getResources().getColor(R.color.primaryLightColor));
                }else {
                    FavoriteDataSource dataSource = new FavoriteDataSource(mMusic.getMusicId());
                    AppDatabase.getInstance(getActivity()).favoriteDao().insertAll(dataSource);
                    mFavorite.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                }
                mFavoriteBool = !mFavoriteBool;
            }
        });

        mLyricIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LyricEntryActivity.newIntent(getContext(), mMusic.getMusicId());
                startActivity(intent);
            }
        });

        mShowLyric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLyric == null)
                    Toaster.makeToast(getContext(), "please create lyric for this music");
                else {
                    if (mLyricText.getVisibility() == View.VISIBLE) {
                        mLyricText.setVisibility(View.GONE);
                        mShowLyric.setColorFilter(getContext().getResources().getColor(R.color.primaryTextColor));
                    }
                    else {
                        mLyricText.setVisibility(View.VISIBLE);
                        mShowLyric.setColorFilter(getContext().getResources().getColor(R.color.primaryOrangeColor));
                    }
                }
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
      mHandler.postDelayed(UpdateSongTime, 100);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            if (getView() != null) {
                mCurrent = mMediaPlayer.getCurrentPosition();
                mCurrentTime.setText(createTimerLabel((int) mCurrent));
                mSeekBar.setProgress((int) mCurrent);
                mHandler.postDelayed(this, 100);
                if (mLyric == null) {
                    return;
                }
                for (int i = 0; i < mLyric.getTexts().size(); i++) {
                    if (mRanges.get(i).contains((double) mMediaPlayer.getCurrentPosition() / 1000)) {
                        mLyricText.setText(mLyric.getTexts().get(i));
                    }
                }
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

    public void startMusic(){
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
            mPlay.setImageResource(R.drawable.ic_play_icon);
        }
        else {
            mMediaPlayer.start();
            mPlay.setImageResource(R.drawable.ic_pause_icon);
        }
    }

    private void findViews(View view) {
        mMusicCover = view.findViewById(R.id.play_cover);
        mPlay = view.findViewById(R.id.play_play);
        mPrevious = view.findViewById(R.id.play_previous);
        mNext = view.findViewById(R.id.play_next);
        mRepeat = view.findViewById(R.id.play_repeat);
        mRandom = view.findViewById(R.id.play_random);
        mMusicName = view.findViewById(R.id.play_music_name);
        mArtistName = view.findViewById(R.id.play_artist_name);
        mSeekBar = view.findViewById(R.id.play_seekBar);
        mCurrentTime = view.findViewById(R.id.play_start_time);
        mTotalTime = view.findViewById(R.id.play_end_time);
        mBackground = view.findViewById(R.id.background_image);
        mFavorite = view.findViewById(R.id.play_favorite);
        mLyricIcon = view.findViewById(R.id.play_lyric);
        mShowLyric = view.findViewById(R.id.play_show_lyric);
        mLyricText = view.findViewById(R.id.play_lyric_text);
    }
}