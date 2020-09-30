package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private static final String ARGS_NAME = "com.example.heroesMusic.utils_name";
    private RecyclerView mMusicRecyclerView;
    private List<Music> mMusicList;
    private MusicAdapter mMusicAdapter;
    private String mName;

    public MusicListFragment() {
        // Required empty public constructor
    }

    public static MusicListFragment newInstance(String name) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicList = new ArrayList<>();
        if (getArguments().getString(ARGS_NAME) != null)
            mName = getArguments().getString(ARGS_NAME);
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

    public void updateUi() {
        mMusicList = MusicLab.getInstance(getContext()).getMusicWithName(mName);
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), mMusicList, mName);
            mMusicRecyclerView.setAdapter(mMusicAdapter);
        }else {
            mMusicAdapter.setMusic(mMusicList);
            mMusicAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mMusicRecyclerView = view.findViewById(R.id.music_recycler_view);
    }
}