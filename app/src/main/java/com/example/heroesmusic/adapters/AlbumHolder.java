package com.example.heroesmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.utils.MusicListActivity;

public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mAlbumImage;
    private TextView mArtistName , mAlbumName;
    private Album mAlbum;

    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mAlbumImage = itemView.findViewById(R.id.album_image);
        mAlbumName = itemView.findViewById(R.id.album_name);
        mArtistName = itemView.findViewById(R.id.album_artist);
    }

    public void bind(Album album , Context context){
        mAlbum = album;
        mAlbumName.setText(album.getAlbumName());
        mArtistName.setText(album.getSinger());
        mAlbumImage.setImageBitmap(MusicLab.getInstance(context).getMusicBitmap(album.getAlbumId()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = MusicListActivity.newIntent(v.getContext() , mAlbum.getAlbumName());
        v.getContext().startActivity(intent);
    }
}