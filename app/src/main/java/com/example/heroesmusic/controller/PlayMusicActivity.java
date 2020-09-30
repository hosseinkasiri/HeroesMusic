package com.example.heroesmusic.controller;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXT_POSITION = "com.example.heroesMusic.utils_position";
    private static final String EXT_NAME = "com.example.heroesMusic.utils_singer";

    public static Intent newIntent(Context context, int position, String name){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXT_POSITION , position);
        intent.putExtra(EXT_NAME, name);
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

    public Fragment mFragment() {
        int position = getIntent().getIntExtra(EXT_POSITION , 0);
        String name = getIntent().getStringExtra(EXT_NAME);
        return PlayMusicFragment.newInstance(position, name);
    }
}