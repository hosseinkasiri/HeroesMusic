package com.example.heroesmusic.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigation;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MusicListFragment.newInstance());
        adapter.addFragment(SingerListFragment.newInstance());
        adapter.addFragment(AlbumListFragment.newInstance());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
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
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void findViews(){
        mAppBarLayout = findViewById(R.id.app_bar);
        mToolbar = findViewById(R.id.tool_bar);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager);
    }
}