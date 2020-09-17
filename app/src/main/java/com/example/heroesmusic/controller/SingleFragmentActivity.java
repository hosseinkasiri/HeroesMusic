package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.heroesmusic.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment mFragment();

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
}
