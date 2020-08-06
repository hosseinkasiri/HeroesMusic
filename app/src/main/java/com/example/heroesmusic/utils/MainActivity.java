package com.example.heroesmusic.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.heroesmusic.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private BottomNavigationView mBottomNavigation;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

    }

    private void findViews(){
        mAppBarLayout = findViewById(R.id.app_bar);
        mToolbar = findViewById(R.id.tool_bar);
        mTabLayout = findViewById(R.id.tab_layout);
        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.view_pager);
    }
}