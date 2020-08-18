package com.example.heroesmusic.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.MusicAdapter;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicListFragment extends Fragment {

    private RecyclerView mMusicRecyclerView;
    private List<Music> mMusic;
    private MusicAdapter mMusicAdapter;
    private MusicLab mMusicLab;
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
        mMusic = new ArrayList<>();
        mMusicLab = new MusicLab(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        findViews(view);
        mMusicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MusicLab.getPermission(getActivity());
        updateUi();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateUi() {
        mMusic = mMusicLab.getMusicList();
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), mMusic , mMusicLab);
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