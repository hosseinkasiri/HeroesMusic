package com.example.heroesmusic.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.ViewPagerAdapter;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.MusicLab;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_PERMISSION = 0;
    private BottomNavigationView mBottomNavigation;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , REQ_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);
            }
        }else {
            doStuff();
        }
    }

    private void doStuff() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MusicListFragment.newInstance(null));
        adapter.addFragment(SingerListFragment.newInstance());
        adapter.addFragment(AlbumListFragment.newInstance());
        adapter.addFragment(SearchFragment.newInstance());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
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
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toaster.makeToast(MainActivity.this, "Permission granted");
                        doStuff();
                    }
                } else {
                    Toaster.makeToast(MainActivity.this, "No permission granted!");
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