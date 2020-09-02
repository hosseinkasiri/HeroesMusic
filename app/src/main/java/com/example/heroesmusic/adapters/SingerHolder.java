package com.example.heroesmusic.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.model.Singer;
import com.example.heroesmusic.controller.MusicListActivity;
import com.squareup.picasso.Picasso;

public class SingerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mSingerImage;
    private TextView mSingerName;
    private Singer mSinger;

    public SingerHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mSingerImage = itemView.findViewById(R.id.singer_image_view);
        mSingerName =itemView.findViewById(R.id.singer_name);
    }

    public void bind(Singer singer , Context context){
        mSinger = singer;
        mSingerName.setText(singer.getSingerName());
        setSingerImage(singer, context);
    }

    private void setSingerImage(Singer singer, Context context) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, singer.getAlbumId());
        Picasso.with(context)
                .load(albumArtUri)
                .placeholder(R.drawable.default_music_cover)
                .resize(200, 200)
                .error(R.drawable.default_music_cover)
                .into(mSingerImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = MusicListActivity.newIntent(v.getContext() , mSinger.getSingerName());
        v.getContext().startActivity(intent);
    }
}
