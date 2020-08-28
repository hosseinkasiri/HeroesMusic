package com.example.heroesmusic.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.heroesmusic.R;

public class MusicListActivity extends AppCompatActivity {

    private static final String EXT_SINGER = "com.example.heroesMusic.utils_singerName";
    public static Intent newIntent(Context context , String singerName){
        Intent intent = new Intent(context , MusicListActivity.class);
        intent.putExtra(EXT_SINGER , singerName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.activity_music_list) == null){

            manager.beginTransaction()
                    .add(R.id.activity_music_list , mFragment())
                    .commit();
        }
    }

    public Fragment mFragment() {
        String singerName = getIntent().getStringExtra(EXT_SINGER);
        return MusicListFragment.newInstance(singerName);
    }
}
