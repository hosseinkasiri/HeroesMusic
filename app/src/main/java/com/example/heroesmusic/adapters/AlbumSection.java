package com.example.heroesmusic.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.model.Singer;
import com.example.heroesmusic.utils.ListMode;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class AlbumSection extends Section implements Filterable {
    private List<Album> mAlbumList;
    private List<Album> mAllAlbum;
    private Context mContext;

    public AlbumSection(List<Album> albumList, Context context) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_music)
                .headerResourceId(R.layout.row_section_header)
                .footerResourceId(R.layout.row_section_footer)
                .build());
        mAlbumList = albumList;
        mAllAlbum = new ArrayList<>(albumList);
        mContext = context;
    }

    public List<Album> getAlbumList() {
        return mAlbumList;
    }

    public void setAlbumList(List<Album> albumList) {
        mAlbumList = albumList;
        mAllAlbum = new ArrayList<>(albumList);
    }

    @Override
    public int getContentItemsTotal() {
        return Math.min(mAlbumList.size(), 3);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new AlbumHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        AlbumHolder albumHolder= (AlbumHolder) viewHolder;
        albumHolder.bind(mAlbumList.get(i), mContext);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Album> filterList = new ArrayList<>();
            if (constraint.toString().isEmpty())
                filterList.addAll(mAllAlbum);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Album album : mAllAlbum){
                    if (album.getAlbumName().toLowerCase().contains(filterPattern))
                        filterList.add(album);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAlbumList.clear();
            mAlbumList.addAll((ArrayList) results.values);
                setHasFooter(mAlbumList.size() > 3);
                setHasHeader(mAlbumList.size() != 0);
        }
    };

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view, ListMode.album);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view, ListMode.album);
    }
}
