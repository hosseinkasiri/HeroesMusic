package com.example.heroesmusic.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.model.Singer;

public class SingerHolder extends RecyclerView.ViewHolder {

    private ImageView mSingerImage;
    private TextView mSingerName;

    public SingerHolder(@NonNull View itemView) {
        super(itemView);
        mSingerImage = itemView.findViewById(R.id.singer_image);
        mSingerName =itemView.findViewById(R.id.singer_name);
    }

    public void bind(Singer singer , Context context){
        mSingerName.setText(singer.getSingerName());
        mSingerImage.setImageBitmap(MusicLab.getInstance(context).getMusicBitmap(singer.getAlbumId()));
    }
}
