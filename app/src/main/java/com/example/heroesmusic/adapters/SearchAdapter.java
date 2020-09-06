package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> implements Filterable {

    private List<Music> mMusicList;
    private List<Music> mAllMusic;
    private Context mContext;

    public SearchAdapter(Context context, List<Music> musicList) {
        mMusicList = new ArrayList<>();
        mAllMusic = new ArrayList<>(musicList);
        mContext = context;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Music music = mMusicList.get(position);
        holder.bind(music, mContext, mMusicList);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Music> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty())
                filterList.addAll(mAllMusic);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Music music : mAllMusic){
                    if (music.getMusicName().toLowerCase().contains(filterPattern) ||
                            music.getSinger().toLowerCase().contains(filterPattern) ||
                            music.getAlbum().toLowerCase().contains(filterPattern))
                        filterList.add(music);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMusicList.clear();
            mMusicList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
