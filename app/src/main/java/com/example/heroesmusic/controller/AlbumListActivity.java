package com.example.heroesmusic.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.heroesmusic.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumListActivity extends SingleFragmentActivity {

    private static final String EXT_ALBUMS = "com.example.heroes_music.controller_albums";

    public static Intent newIntent(Context context, List<Album> albums){
        Intent intent = new Intent(context, AlbumListActivity.class);
        intent.putExtra(EXT_ALBUMS, (ArrayList<? extends Parcelable>) albums);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        List<Album> albums = getIntent().getParcelableArrayListExtra(EXT_ALBUMS);
        return AlbumListFragment.newInstance(albums);
    }
}