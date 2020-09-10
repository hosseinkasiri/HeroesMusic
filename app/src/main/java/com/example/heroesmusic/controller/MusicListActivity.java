package com.example.heroesmusic.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends SingleFragmentActivity {

    private static final String EXT_NAME = "com.example.heroesMusic.utils_singerName";
    private String name;

    public static Intent newIntent(Context context , String name){
        Intent intent = new Intent(context , MusicListActivity.class);
        intent.putExtra(EXT_NAME, name);
        return intent;
    }

    public Fragment mFragment() {
        name = getIntent().getStringExtra(EXT_NAME);
        return MusicListFragment.newInstance(name);
    }
}
