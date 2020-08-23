package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.model.MusicLab;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter <MusicHolder>{
    private Context mContext;
    private List<Music> mMusic;
    private String mSingerName;

    public MusicAdapter(Context context , List<Music> music , String singerName) {
        mContext = context;
        mMusic = new ArrayList<>(music);
        mSingerName = singerName;
    }

    public void setMusic(List<Music> music) {
        mMusic = music;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_music,parent,false);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Music music = mMusic.get(position);
        holder.bind(music , mContext , mSingerName);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }
}
