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
import com.example.heroesmusic.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {

    private Context mContext;
    private List<Album> mAlbums;

    public AlbumAdapter(Context context,List<Album> albums) {
        mContext = context;
        mAlbums = new ArrayList<>(albums);
    }

    public void setAlbums(List<Album> albums) {
        mAlbums = albums;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_music,parent,false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = mAlbums.get(position);
        holder.bind(album , mContext);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public static class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            Glide
                    .with(context)
                    .load(albumArtUri)
                    .centerCrop()
                    .placeholder(R.drawable.default_music_cover)
                    .into(mAlbumImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MusicListActivity.newIntent(v.getContext(), mAlbum.getAlbumName());
            v.getContext().startActivity(intent);
        }
    }
}
