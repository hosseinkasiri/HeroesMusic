package com.example.heroesmusic.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.helper.PictureUtils;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.io.IOException;

public class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMusicImage;
    private TextView mMusicName , mMusicArtist;
    private Music mMusic;
    private MusicLab mMusicLab;

    public MusicHolder(@NonNull View itemView , MusicLab musicLab) {
        super(itemView);
        findViews(itemView);
        mMusicLab = musicLab;
    }

    private void findViews(@NonNull View itemView) {
        itemView.setOnClickListener(this);
        mMusicImage = itemView.findViewById(R.id.music_image);
        mMusicName = itemView.findViewById(R.id.music_name);
        mMusicArtist = itemView.findViewById(R.id.music_artist);
    }

    public void bind(Music music , Context context) {
        mMusicName.setText(music.getMusicName());
        mMusicArtist.setText(music.getSinger());
        mMusicImage.setImageBitmap(music.getAlbumArtBitmap());
        mMusic = music;
    }

    @Override
    public void onClick(View v) {
            mMusicLab.startMusic(mMusic);
    }
}