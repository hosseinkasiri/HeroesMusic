package com.example.heroesmusic.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.heroesmusic.R;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class PlayMusicActivity extends AppCompatActivity {

    private static final String EXT_POSITION = "com.example.heroesMusic.utils_position";
    private static final String EXT_SINGER = "com.example.heroesMusic.utils_singer";
    public static Intent newIntent(Context context , int position , String singerName){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXT_POSITION , position);
        intent.putExtra(EXT_SINGER , singerName);
        return intent;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |  SYSTEM_UI_FLAG_LAYOUT_STABLE);
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
        int position = getIntent().getIntExtra(EXT_POSITION , 0);
        String singerName = getIntent().getStringExtra(EXT_SINGER);
        return PlayMusicFragment.newInstance(position , singerName);
    }
}