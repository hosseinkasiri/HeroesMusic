package com.example.heroesmusic.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LyricEntryActivity extends SingleFragmentActivity {

    private static final String MUSIC_ID = "com.example.heroesMusic.controller_lyricId";

    public static Intent newIntent(Context context, Long musicId){
        Intent intent = new Intent(context, LyricEntryActivity.class);
        intent.putExtra(MUSIC_ID, musicId);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        long lyricId = getIntent().getLongExtra(MUSIC_ID, 0);
        return LyricEntryFragment.newInstance(lyricId);
    }
}