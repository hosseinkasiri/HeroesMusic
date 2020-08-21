package com.example.heroesmusic.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.heroesmusic.model.Music;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXTRA_POSITION = "com.example.heroesMusic.utils_position";
    public static Intent newIntent(Context context ,int position){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXTRA_POSITION , position);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        int position = getIntent().getIntExtra(EXTRA_POSITION , 0);
        return PlayMusicFragment.newInstance(position);
    }
}