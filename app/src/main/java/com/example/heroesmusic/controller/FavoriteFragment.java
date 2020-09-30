package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.FavoriteAdapter;
import com.example.heroesmusic.database.AppDatabase;
import com.example.heroesmusic.database.FavoriteDataSource;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView mFavoriteRecycler;
    private List<Music> mMusicList;
    private FavoriteAdapter mFavoriteAdapter;
    private List<Long> mFavoriteId;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        findViews(view);
        mFavoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updateUi();
        return view;
    }

    public void updateUi() {
        mMusicList = MusicLab.getInstance(getContext()).getFavoriteMusic();
        if (mFavoriteAdapter == null) {
            mFavoriteAdapter = new FavoriteAdapter(getContext(), mMusicList);
            mFavoriteRecycler.setAdapter(mFavoriteAdapter);
        }else {
            mFavoriteAdapter.setMusicList(mMusicList);
            mFavoriteAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mFavoriteRecycler = view.findViewById(R.id.favorite_recycler_view);
    }
}