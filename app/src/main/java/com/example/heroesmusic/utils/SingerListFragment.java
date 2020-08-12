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
import com.example.heroesmusic.adapters.SingerAdapter;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.model.Singer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SingerListFragment extends Fragment {

    private RecyclerView mSingerRecyclerView;
    private List<Singer> mSingers;
    private SingerAdapter mSingerAdapter;

    public SingerListFragment() {
        // Required empty public constructor
    }

    public static SingerListFragment newInstance() {
        SingerListFragment fragment = new SingerListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singer_list, container, false);
        findViews(view);
        mSingerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    private void updateUi() {
        mSingers = MusicLab.getSingers(MusicLab.getMusic(getActivity()));
        if (mSingerAdapter == null) {
            mSingerAdapter = new SingerAdapter(getActivity(), mSingers);
            mSingerRecyclerView.setAdapter(mSingerAdapter);
        }else {
            mSingerAdapter.setSingers(mSingers);
            mSingerAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mSingerRecyclerView = view.findViewById(R.id.singer_recycler_view);
    }
}