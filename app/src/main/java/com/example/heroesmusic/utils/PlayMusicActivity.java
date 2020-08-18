package com.example.heroesmusic.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PlayMusicActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        return PlayMusicFragment.newInstance();
    }
}