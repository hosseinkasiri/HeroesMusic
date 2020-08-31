package com.example.heroesmusic.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigation;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
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

    private void findViews(){
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager);
    }
}