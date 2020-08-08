package com.example.heroesmusic.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.AlbumAdapter;

public class AlbumListFragment extends Fragment {

    private RecyclerView mAlbumRecyclerView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        findViews(view);
        mAlbumRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlbumAdapter albumAdapter = new AlbumAdapter(getActivity());
        mAlbumRecyclerView.setAdapter(albumAdapter);
        return view;
    }

    private void findViews(View view) {
        mAlbumRecyclerView = view.findViewById(R.id.album_recycler_view);
    }
}