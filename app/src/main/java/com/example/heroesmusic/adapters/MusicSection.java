package com.example.heroesmusic.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Music;
import com.example.heroesmusic.utils.FilterableSection;
import com.example.heroesmusic.utils.ListMode;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class MusicSection extends Section implements FilterableSection {
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
        setHasFooter(mMusicList.size() > 3);
        setHasHeader(mMusicList.size() > 0);
        mContext = context;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
        mAllMusic = new ArrayList<>(musicList);
    }

    @Override
    public int getContentItemsTotal() {
        return Math.min(mMusicList.size(), 3);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MusicAdapter.MusicHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        MusicAdapter.MusicHolder musicHolder = (MusicAdapter.MusicHolder) viewHolder;
        musicHolder.bind(mMusicList.get(i), mContext, 0, mMusicList.get(i).getMusicName());
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view, ListMode.music);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view, ListMode.music);
    }
    @Override
    public void filter(@NonNull final String query) {
        if (TextUtils.isEmpty(query)) {
            mMusicList.clear();
            mMusicList.addAll(mAllMusic);
            this.setVisible(true);
        } else {
            mMusicList.clear();
            for (final Music music : mAllMusic) {
                if (music.getMusicName().toLowerCase()
                        .contains(query.toLowerCase())) {
                    mMusicList.add(music);
                }
            }
            this.setVisible(!mMusicList.isEmpty());
            setHasFooter(mMusicList.size() > 3);
        }
        setHasFooter(mMusicList.size() > 3);
    }
}
