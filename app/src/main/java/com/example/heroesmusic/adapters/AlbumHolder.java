package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.MusicLab;

public class AlbumHolder extends RecyclerView.ViewHolder {

    private ImageView mAlbumImage;
    private TextView mArtistName , mAlbumName;

    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        mAlbumImage = itemView.findViewById(R.id.album_image);
        mAlbumName = itemView.findViewById(R.id.album_name);
        mArtistName = itemView.findViewById(R.id.album_artist);
    }

    public void bind(Album album , Context context){
        mAlbumName.setText(album.getAlbumName());
        mArtistName.setText(album.getSinger());
        mAlbumImage.setImageBitmap(MusicLab.getInstance(context).getMusicBitmap(album.getAlbumId()));
    }
}
