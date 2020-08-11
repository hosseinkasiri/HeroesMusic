package com.example.heroesmusic.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.helper.PictureUtils;
import com.example.heroesmusic.model.Music;

public class MusicHolder extends RecyclerView.ViewHolder {

    private ImageView mMusicImage;
    private TextView mMusicName , mMusicArtist;

    public MusicHolder(@NonNull View itemView) {
        super(itemView);
        findViews(itemView);
    }

    private void findViews(@NonNull View itemView) {
        mMusicImage = itemView.findViewById(R.id.music_image);
        mMusicName = itemView.findViewById(R.id.music_name);
        mMusicArtist = itemView.findViewById(R.id.music_artist);
    }

    public void bind(Music music , Context context){
        mMusicName.setText(music.getMusicName());
        mMusicArtist.setText(music.getSinger());
        Bitmap bitmap = PictureUtils.getScaledBitmap(music.getMusicPath(),(Activity)context);
        mMusicImage.setImageBitmap(bitmap);
    }
}
