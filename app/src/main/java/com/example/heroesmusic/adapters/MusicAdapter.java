package com.example.heroesmusic.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heroesmusic.R;
import com.example.heroesmusic.controller.PlayMusicActivity;
import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter <MusicAdapter.MusicHolder>{
    private Context mContext;
    private List<Music> mMusic;
    private String mName;

    public MusicAdapter(Context context, List<Music> music, String name) {
        mContext = context;
        mMusic = new ArrayList<>(music);
        mName = name;
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
        holder.bind(music, mContext, position, mName);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public static class MusicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mMusicImage;
        private TextView mMusicName , mMusicArtist;
        private int mPosition;
        private String mName;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(@NonNull View itemView) {
            itemView.setOnClickListener(this);
            mMusicImage = itemView.findViewById(R.id.music_image_view);
            mMusicName = itemView.findViewById(R.id.music_name);
            mMusicArtist = itemView.findViewById(R.id.music_artist);
        }

        public void bind(Music music, Context context, int position, String name) {
            mPosition = position;
            mName = name;
            mMusicName.setText(music.getMusicName());
            mMusicArtist.setText(music.getSinger());
            setMusicImage(music, context);
        }

        private void setMusicImage(Music music, Context context) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, music.getAlbumId());
            Glide
                    .with(context)
                    .load(albumArtUri)
                    .centerCrop()
                    .placeholder(R.drawable.default_music_cover)
                    .into(mMusicImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = PlayMusicActivity.newIntent(v.getContext(), mPosition, mName);
            v.getContext().startActivity(intent);
        }
    }
}
