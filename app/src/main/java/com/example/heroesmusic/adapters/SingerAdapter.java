package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Singer;

import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerHolder> {

    private Context mContext;
    private List<Singer> mSingers;

    public SingerAdapter(Context context) {
        mContext = context;
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
        holder.bind(singer);
    }

    @Override
    public int getItemCount() {
        return mSingers.size();
    }
}
