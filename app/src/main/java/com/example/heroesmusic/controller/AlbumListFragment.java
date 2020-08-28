package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.AlbumAdapter;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.AlbumLab;

import java.util.ArrayList;
import java.util.List;

public class AlbumListFragment extends Fragment {

    private RecyclerView mAlbumRecyclerView;
    private List<Album> mAlbums;
    private AlbumAdapter mAlbumAdapter;

    public AlbumListFragment() {
        // Required empty public constructor
    }

    public static AlbumListFragment newInstance() {
        AlbumListFragment fragment = new AlbumListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbums = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        findViews(view);
        mAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    private void updateUi() {
        mAlbums = AlbumLab.getInstance(getActivity()).getAlbums();
        if (mAlbumAdapter == null) {
            mAlbumAdapter = new AlbumAdapter(getActivity(), mAlbums);
            mAlbumRecyclerView.setAdapter(mAlbumAdapter);
        }else {
            mAlbumAdapter.setAlbums(mAlbums);
            mAlbumAdapter.notifyDataSetChanged();
        }
    }

    private void findViews(View view) {
        mAlbumRecyclerView = view.findViewById(R.id.album_recycler_view);
    }
}