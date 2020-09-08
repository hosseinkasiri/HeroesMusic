package com.example.heroesmusic.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXT_POSITION = "com.example.heroesMusic.utils_position";
    private static final String EXT_LIST = "com.example.heroesMusic.utils_singer";

    public static Intent newIntent(Context context, int position, List<Music> music){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXT_POSITION , position);
        intent.putParcelableArrayListExtra(EXT_LIST, (ArrayList<? extends Parcelable>) music);
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
        List<Music> music = getIntent().getParcelableArrayListExtra(EXT_LIST);
        return PlayMusicFragment.newInstance(position, music);
    }
}