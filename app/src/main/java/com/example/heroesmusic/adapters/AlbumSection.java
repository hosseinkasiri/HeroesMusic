package com.example.heroesmusic.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Album;
import com.example.heroesmusic.utils.FilterableSection;
import com.example.heroesmusic.utils.ListMode;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class AlbumSection extends Section implements FilterableSection {
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
        return new AlbumAdapter.AlbumHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        AlbumAdapter.AlbumHolder albumHolder= (AlbumAdapter.AlbumHolder) viewHolder;
        albumHolder.bind(mAlbumList.get(i), mContext);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view, ListMode.album);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view, ListMode.album);
    }

    @Override
    public void filter(@NonNull final String query) {
        if (TextUtils.isEmpty(query)) {
            mAlbumList.clear();
            mAlbumList.addAll(mAllAlbum);
            this.setVisible(true);
        } else {
            mAlbumList.clear();
            for (final Album album : mAllAlbum) {
                if (album.getAlbumName().toLowerCase()
                        .contains(query.toLowerCase())) {
                    mAlbumList.add(album);
                }
            }
            this.setVisible(!mAlbumList.isEmpty());
            setHasFooter(mAlbumList.size() > 3);
        }
        setHasFooter(mAlbumList.size() > 3);
    }
}
