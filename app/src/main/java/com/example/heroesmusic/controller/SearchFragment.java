package com.example.heroesmusic.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.AlbumSection;
import com.example.heroesmusic.adapters.FooterViewHolder;
import com.example.heroesmusic.adapters.SingerAdapter;
import com.example.heroesmusic.adapters.SingerSection;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.AlbumLab;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.adapters.MusicSection;
import com.example.heroesmusic.model.Singer;
import com.example.heroesmusic.model.SingerLab;
import com.example.heroesmusic.utils.FilterableSection;
import com.example.heroesmusic.utils.ListMode;
import com.example.heroesmusic.utils.ShowMoreEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class SearchFragment extends Fragment {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private List<Music> mMusicList;
    private List<Singer> mSingers;
    private List<Album> mAlbums;
    private MusicSection mMusicAdapter;
    private SingerSection mSingerAdapter;
    private AlbumSection mAlbumAdapter;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private String mNewText;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicList = MusicLab.getInstance(getActivity()).getMusicList();
        mSingers = SingerLab.getInstance(getActivity()).getSingers();
        mAlbums = AlbumLab.getInstance(getActivity()).getAlbums();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        handelSearchView();
        return view;
    }

    private void updateUi() {
        if (mMusicAdapter == null && mSingerAdapter == null && mAlbumAdapter == null) {
            mMusicAdapter = new MusicSection(mMusicList, getContext());
            mSingerAdapter = new SingerSection(mSingers, getContext());
            mAlbumAdapter = new AlbumSection(mAlbums, getContext());
            sectionAdapter.addSection(mMusicAdapter);
            sectionAdapter.addSection(mSingerAdapter);
            sectionAdapter.addSection(mAlbumAdapter);
            mRecyclerView.setAdapter(sectionAdapter);
        }else {
            mMusicAdapter.setMusicList(mMusicList);
            mSingerAdapter.setSingerList(mSingers);
            mAlbumAdapter.setAlbumList(mAlbums);
            sectionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    private void handelSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mNewText = newText;
                for (Section section : sectionAdapter.getCopyOfSectionsMap().values()) {
                    if (section instanceof FilterableSection) {
                        ((FilterableSection) section).filter(newText);
                    }
                }
                sectionAdapter.notifyDataSetChanged();
                return true;
            }
        });
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
    }

    private void findViews(View view) {
        mSearchView = view.findViewById(R.id.search_view);
        mRecyclerView = view.findViewById(R.id.search_recycler_view);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMoreEvent(ShowMoreEvent event) {
        switch (event.getListMode()){
            case music:
                if (mNewText == null)
                    mNewText = "all music";
                Intent intent = MusicListActivity.newIntent(getContext(), mNewText);
                startActivity(intent);
                break;
            case singer:
                Intent singerIntent = SingerListActivity.newIntent(getContext(), mSingerAdapter.getSingerList());
                startActivity(singerIntent);
                break;
            case album:
                Intent albumIntent = AlbumListActivity.newIntent(getContext(), mAlbumAdapter.getAlbumList());
                startActivity(albumIntent);
                break;
        }
    }
}