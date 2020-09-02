package com.example.heroesmusic.controller;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.MusicAdapter;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.utils.MediaPlayerGlobal;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MusicListFragment extends Fragment {

    private static final String ARGS_SINGER = "com.example.heroesMusic.utils_singerName";
    private RecyclerView mMusicRecyclerView;
    private List<Music> mMusic;
    private MusicAdapter mMusicAdapter;
    private MusicLab mMusicLab;
    private String mSingerName;
    public MusicListFragment() {
        // Required empty public constructor
    }

    public static MusicListFragment newInstance(String singerName) {
        MusicListFragment fragment = new MusicListFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_SINGER , singerName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusic = new ArrayList<>();
        mMusicLab = MusicLab.getInstance(getActivity());
        if (getArguments() != null)
            mSingerName = getArguments().getString(ARGS_SINGER);

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

    private void updateUi() {
        mMusic = mMusicLab.getMusic(mSingerName);
        if (mMusicAdapter == null) {
            mMusicAdapter = new MusicAdapter(getActivity(), mMusic);
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