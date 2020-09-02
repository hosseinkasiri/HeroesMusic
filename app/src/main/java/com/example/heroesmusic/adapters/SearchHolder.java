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
import com.example.heroesmusic.controller.PlayMusicActivity;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMusicImage;
    private TextView mMusicName , mMusicArtist;
    private Context mContext;
    private List<Music> mMusicList;

    public SearchHolder(@NonNull View itemView) {
        super(itemView);
        findViews(itemView);
    }

    public void bind(Music music, Context context, List<Music> musicList){
        mContext = context;
        mMusicList = musicList;
        mMusicName.setText(music.getMusicName());
        mMusicArtist.setText(music.getSinger());
        setMusicImage(music, context);
    }

    private void setMusicImage(Music music, Context context) {
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, music.getAlbumId());
        Picasso.with(context)
                .load(albumArtUri)
                .placeholder(R.drawable.default_music_cover)
                .resize(200, 200)
                .error(R.drawable.default_music_cover)
                .into(mMusicImage);
    }

    private void findViews(@NonNull View itemView) {
        itemView.setOnClickListener(this);
        mMusicImage = itemView.findViewById(R.id.music_image_view);
        mMusicName = itemView.findViewById(R.id.music_name);
        mMusicArtist = itemView.findViewById(R.id.music_artist);
    }

    @Override
    public void onClick(View v) {
        Intent intent = PlayMusicActivity.newIntent(mContext, getAdapterPosition(), mMusicList);
        v.getContext().startActivity(intent);
    }
}
