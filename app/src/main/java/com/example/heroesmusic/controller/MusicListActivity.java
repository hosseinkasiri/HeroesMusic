package com.example.heroesmusic.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends SingleFragmentActivity {

    private static final String EXT_MUSIC = "com.example.heroesMusic.utils_singerName";
    public static Intent newIntent(Context context , List<Music> musicList){
        Intent intent = new Intent(context , MusicListActivity.class);
        intent.putExtra(EXT_MUSIC, (ArrayList<? extends Parcelable>) musicList);
        return intent;
    }

    public Fragment mFragment() {
        List<Music> musicList = getIntent().getParcelableArrayListExtra(EXT_MUSIC);
        return MusicListFragment.newInstance(musicList);
    }
}
