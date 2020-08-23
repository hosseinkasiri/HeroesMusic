package com.example.heroesmusic.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.heroesmusic.model.Music;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXT_POSITION = "com.example.heroesMusic.utils_position";
    private static final String EXT_SINGER = "com.example.heroesMusic.utils_singer";
    public static Intent newIntent(Context context ,int position , String singerName){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXT_POSITION , position);
        intent.putExtra(EXT_SINGER , singerName);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        int position = getIntent().getIntExtra(EXT_POSITION , 0);
        String singerName = getIntent().getStringExtra(EXT_SINGER);
        return PlayMusicFragment.newInstance(position , singerName);
    }
}