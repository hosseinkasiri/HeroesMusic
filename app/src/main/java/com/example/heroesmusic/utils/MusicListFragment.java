package com.example.heroesmusic.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.MusicAdapter;

public class MusicListFragment extends Fragment {

    private RecyclerView mMusicRecyclerView;

    public MusicListFragment() {
        // Required empty public constructor
    }

    public static MusicListFragment newInstance() {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        findViews(view);
        mMusicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MusicAdapter musicAdapter = new MusicAdapter(getActivity());
        mMusicRecyclerView.setAdapter(musicAdapter);
        return view;
    }

    private void findViews(View view) {
        mMusicRecyclerView = view.findViewById(R.id.music_recycler_view);
    }
}