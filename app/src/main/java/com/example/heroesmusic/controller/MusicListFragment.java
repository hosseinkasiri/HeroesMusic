package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.MusicAdapter;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.util.ArrayList;
import java.util.List;

public class MusicListFragment extends Fragment {

    private static final String ARGS_MUSIC = "com.example.heroesMusic.utils_music";
    private RecyclerView mMusicRecyclerView;
    private List<Music> mMusic;
    private MusicAdapter mMusicAdapter;

    public MusicListFragment() {
        // Required empty public constructor
    }

    public static MusicListFragment newInstance(List<Music> music) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_MUSIC, (ArrayList<? extends Parcelable>) music);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusic = new ArrayList<>();
        if (getArguments() != null)
            mMusic = getArguments().getParcelableArrayList(ARGS_MUSIC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        findViews(view);
        mMusicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    private void updateUi() {
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), mMusic);
            mMusicRecyclerView.setAdapter(mMusicAdapter);
        }else {
            mMusicAdapter.setMusic(mMusic);
            mMusicAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mMusicRecyclerView = view.findViewById(R.id.music_recycler_view);
    }
}