package com.example.heroesmusic.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.heroesmusic.utils.MusicListActivity;

public class SingerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mSingerImage;
    private TextView mSingerName;
    private Singer mSinger;

    public SingerHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mSingerImage = itemView.findViewById(R.id.singer_image);
        mSingerName =itemView.findViewById(R.id.singer_name);
    }

    public void bind(Singer singer , Context context){
        mSinger = singer;
        mSingerName.setText(singer.getSingerName());
        mSingerImage.setImageBitmap(MusicLab.getInstance(context).getMusicBitmap(singer.getAlbumId()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = MusicListActivity.newIntent(v.getContext() , mSinger.getSingerName());
        v.getContext().startActivity(intent);
    }
}
