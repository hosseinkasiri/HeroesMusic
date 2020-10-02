package com.example.heroesmusic.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LyricEntryActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LyricEntryActivity.class);
        return intent;
    }

    @Override
    public Fragment mFragment() {
        return LyricEntryFragment.newInstance();
    }
}