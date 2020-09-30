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

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>{

    private List<Music> mMusicList;
    private Context mContext;

    public FavoriteAdapter(Context context, List<Music> musicList) {
        mMusicList = musicList;
        mContext = context;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        Music music = mMusicList.get(position);
        holder.bind(music);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private TextView mMusicName, mArtistName;

        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.music_image_view);
            mMusicName = itemView.findViewById(R.id.music_name);
            mArtistName = itemView.findViewById(R.id.music_artist);
        }

        public void bind(Music music){
            mMusicName.setText(music.getMusicName());
            mArtistName.setText(music.getSinger());
            setMusicImage(music, mContext);
        }

        private void setMusicImage(Music music, Context context) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, music.getAlbumId());
            Glide
                    .with(context)
                    .load(albumArtUri)
                    .centerCrop()
                    .placeholder(R.drawable.default_music_cover)
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = PlayMusicActivity.newIntent(mContext, getAdapterPosition(), "favorite");
            v.getContext().startActivity(intent);
        }
    }
}