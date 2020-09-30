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
import com.example.heroesmusic.controller.MusicListActivity;
import com.example.heroesmusic.model.Singer;

import java.util.ArrayList;
import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> {

    private Context mContext;
    private List<Singer> mSingers;

    public SingerAdapter(Context context , List<Singer> singers) {
        mContext = context;
        mSingers = new ArrayList<>(singers);
    }

    public void setSingers(List<Singer> singers) {
        mSingers = singers;
    }

    @NonNull
    @Override
    public SingerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_singer,parent,false);
        return new SingerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerHolder holder, int position) {
        Singer singer = mSingers.get(position);
        holder.bind(singer , mContext);
    }

    @Override
    public int getItemCount() {
        return mSingers.size();
    }

    public static class SingerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            Glide
                    .with(context)
                    .load(albumArtUri)
                    .centerCrop()
                    .placeholder(R.drawable.default_music_cover)
                    .into(mSingerImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MusicListActivity.newIntent(v.getContext(), mSinger.getSingerName());
            v.getContext().startActivity(intent);
        }
    }
}
