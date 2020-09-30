package com.example.heroesmusic.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.ViewPagerAdapter;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.AlbumLab;
import com.example.heroesmusic.model.SingerLab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HeroesActivity extends AppCompatActivity {

    private static final int REQ_PERMISSION = 0;
    private BottomNavigationView mBottomNavigation;
    private ViewPager mViewPager;
    private FavoriteFragment mFavoriteFragment;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, HeroesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);
        findViews();
        if (ContextCompat.checkSelfPermission(HeroesActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HeroesActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , REQ_PERMISSION);
        }else {
            doStuff();
        }
    }

    private void doStuff() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFavoriteFragment = FavoriteFragment.newInstance();
        adapter.addFragment(MusicListFragment.newInstance("all music"));
        adapter.addFragment(SingerListFragment.newInstance(SingerLab.getInstance(getBaseContext()).getSingers()));
        adapter.addFragment(AlbumListFragment.newInstance(AlbumLab.getInstance(getBaseContext()).getAlbums()));
        adapter.addFragment(SearchFragment.newInstance());
        adapter.addFragment(mFavoriteFragment);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(5);
        handelViewPager();
        handelBottomNavigation();
    }

    private void handelBottomNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.music:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.singer:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.album:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.search:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.favorite:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });
    }

    private void handelViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4)
                    mFavoriteFragment.updateUi();
                switch (position){
                    case 0:
                        mBottomNavigation.getMenu().findItem(R.id.music).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigation.getMenu().findItem(R.id.singer).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigation.getMenu().findItem(R.id.album).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigation.getMenu().findItem(R.id.search).setChecked(true);
                        break;
                    case 4:
                        mBottomNavigation.getMenu().findItem(R.id.favorite).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQ_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(HeroesActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toaster.makeToast(HeroesActivity.this, "Permission granted");
                        doStuff();
                    }
                } else {
                    Toaster.makeToast(HeroesActivity.this, "No permission granted!");
                    finish();
                }
                break;
        }
    }

    private void findViews(){
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager);
    }
}