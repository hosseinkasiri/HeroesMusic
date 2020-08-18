package com.example.heroesmusic.utils;

import android.os.Bundle;
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

public class PlayMusicFragment extends Fragment {

    private ImageView mMusicCover , mPlayImage , mPreviousImage;
    private ImageView mNextImage,mRepeatImage , mRandomImage;
    private TextView mMusicName , mArtistName;
    private SeekBar mSeekBar;


    public static PlayMusicFragment newInstance() {
        Bundle args = new Bundle();
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music , container , false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        mMusicCover = view.findViewById(R.id.play_cover);
        mPlayImage = view.findViewWithTag(R.id.play_play);
        mPreviousImage = view.findViewById(R.id.play_previous);
        mNextImage = view.findViewById(R.id.play_next);
        mRepeatImage = view.findViewById(R.id.play_repeat);
        mRandomImage = view.findViewById(R.id.play_random);
        mMusicName = view.findViewById(R.id.play_music_name);
        mArtistName = view.findViewById(R.id.play_artist_name);
        mSeekBar = view.findViewById(R.id.play_seekBar);
    }
}
