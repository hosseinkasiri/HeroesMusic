package com.example.heroesmusic.controller;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.SearchAdapter;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private List<Music> mMusic;
    private SearchAdapter mSearchAdapter;

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
        mMusic = MusicLab.getInstance(getActivity()).getMusicList();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchAdapter = new SearchAdapter(getActivity() , mMusic);
        mRecyclerView.setAdapter(mSearchAdapter);
        handelSearchView();
        return view;
    }

    private void handelSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchAdapter.getFilter().filter(newText);
                return false;
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
}