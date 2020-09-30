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
import com.example.heroesmusic.adapters.SingerAdapter;
import com.example.heroesmusic.model.Singer;

import java.util.ArrayList;
import java.util.List;

public class SingerListFragment extends Fragment{

    private static final String ARGS_SINGER = "com.example.heroes_music.controller_singer";
    private RecyclerView mSingerRecyclerView;
    private List<Singer> mSingers;
    private SingerAdapter mSingerAdapter;

    public SingerListFragment() {
        // Required empty public constructor
    }

    public static SingerListFragment newInstance(List<Singer> singers) {
        SingerListFragment fragment = new SingerListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_SINGER, (ArrayList<? extends Parcelable>) singers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingers = new ArrayList<>();
        if (getArguments() != null)
            mSingers = getArguments().getParcelableArrayList(ARGS_SINGER);
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