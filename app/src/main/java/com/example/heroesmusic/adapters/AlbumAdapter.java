package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

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
}
