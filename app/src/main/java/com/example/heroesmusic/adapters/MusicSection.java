package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.adapters.MusicHolder;
import com.example.heroesmusic.controller.SearchFragment;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.utils.ListMode;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class MusicSection extends Section implements Filterable {
    private List<Music> mMusicList;
    private List<Music> mAllMusic;
    private Context mContext;

    public MusicSection(List<Music> musicList, Context context) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_music)
                .headerResourceId(R.layout.row_section_header)
                .footerResourceId(R.layout.row_section_footer)
                .build());
        mMusicList = new ArrayList<>(musicList);
        mAllMusic = new ArrayList<>(musicList);
        mContext = context;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
        mAllMusic = new ArrayList<>(musicList);
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    @Override
    public int getContentItemsTotal() {
        return Math.min(mMusicList.size(), 3);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MusicHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MusicHolder musicHolder = (MusicHolder) viewHolder;
        musicHolder.bind(mMusicList.get(i), mContext, mMusicList, i);
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
                    if (music.getMusicName().toLowerCase().contains(filterPattern))
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
            setHasFooter(mMusicList.size() > 3);
            setHasHeader(mMusicList.size() != 0);
        }
    };

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view, ListMode.music);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view, ListMode.music);
    }
}
