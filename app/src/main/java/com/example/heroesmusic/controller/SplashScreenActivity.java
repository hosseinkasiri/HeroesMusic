package com.example.heroesmusic.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.heroesmusic.R;

public class SplashScreenActivity extends AppCompatActivity {
    /** Duration of wait **/

    private ImageView mImageView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        mImageView = findViewById(R.id.splashscreen);
        bitAnimation();
    }

    private void bitAnimation() {
        Animation bitAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bit_animation);
        bitAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
              scaleAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mImageView.startAnimation(bitAnimation);
    }

    private void scaleAnimation() {
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_animation);
        aniFade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImageView.setVisibility(View.GONE);
                Intent mainIntent = HeroesActivity.newIntent(getBaseContext());
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mImageView.setAnimation(aniFade);
    }
}


