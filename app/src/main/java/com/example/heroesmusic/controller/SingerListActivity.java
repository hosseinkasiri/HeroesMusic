package com.example.heroesmusic.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.heroesmusic.model.Singer;

import java.util.ArrayList;
import java.util.List;

public class SingerListActivity extends SingleFragmentActivity {

    private static final String EXT_SINGER = "com.example.heroes_music.controller_singer";

    public static Intent newIntent(Context context, List<Singer> singers){
        Intent intent = new Intent(context, SingerListActivity.class);
        intent.putExtra(EXT_SINGER, (ArrayList<? extends Parcelable>) singers);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        List<Singer> singers = getIntent().getParcelableArrayListExtra(EXT_SINGER);
        return SingerListFragment.newInstance(singers);
    }
}