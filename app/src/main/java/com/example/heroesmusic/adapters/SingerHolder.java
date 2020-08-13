package com.example.heroesmusic.adapters;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Singer;

public class SingerHolder extends RecyclerView.ViewHolder {

    private ImageView mSingerImage;
    private TextView mSingerName;

    public SingerHolder(@NonNull View itemView) {
        super(itemView);
        mSingerImage = itemView.findViewById(R.id.singer_image);
        mSingerName =itemView.findViewById(R.id.singer_name);
    }

    public void bind(Singer singer){
        mSingerName.setText(singer.getSingerName());
        mSingerImage.setImageBitmap(singer.getSingerAlbumBitmap());
    }
}
