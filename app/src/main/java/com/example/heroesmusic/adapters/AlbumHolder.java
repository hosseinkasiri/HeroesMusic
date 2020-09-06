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
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.example.heroesmusic.controller.MusicListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mAlbumImage;
    private TextView mArtistName , mAlbumName;
    private Album mAlbum;

    public AlbumHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mAlbumImage = itemView.findViewById(R.id.music_image_view);
        mAlbumName = itemView.findViewById(R.id.music_name);
        mArtistName = itemView.findViewById(R.id.music_artist);
    }

    public void bind(Album album , Context context){
        mAlbum = album;
        mAlbumName.setText(album.getAlbumName());
        mArtistName.setText(album.getSinger());
        setAlbumImage(album, context);
    }

    private void setAlbumImage(Album album, Context context) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, album.getAlbumId());
        Picasso.with(context)
                .load(albumArtUri)
                .placeholder(R.drawable.default_music_cover)
                .resize(200, 200)
                .error(R.drawable.default_music_cover)
                .into(mAlbumImage);
    }

    @Override
    public void onClick(View v) {
        List<Music> music = MusicLab.getInstance(v.getContext()).getMusic(mAlbum.getAlbumName());
        Intent intent = MusicListActivity.newIntent(v.getContext(), music);
        v.getContext().startActivity(intent);
    }
}