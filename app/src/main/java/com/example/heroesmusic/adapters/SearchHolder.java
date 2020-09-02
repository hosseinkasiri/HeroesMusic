package com.example.heroesmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.controller.PlayMusicActivity;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.util.List;

public class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMusicImage;
    private TextView mMusicName , mMusicArtist;
    private Music mMusic;
    private Context mContext;
    private List<Music> mMusicList;

    public SearchHolder(@NonNull View itemView) {
        super(itemView);
        findViews(itemView);
    }

    public void bind(Music music, Context context, List<Music> musicList){
        mMusic = music;
        mContext = context;
        mMusicList = musicList;
        mMusicName.setText(music.getMusicName());
        mMusicArtist.setText(music.getSinger());
        mMusicImage.setImageBitmap(MusicLab.getInstance(mContext).getMusicBitmap(mMusic));
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
