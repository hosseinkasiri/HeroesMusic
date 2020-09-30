package com.example.heroesmusic.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.model.Singer;
import com.example.heroesmusic.utils.FilterableSection;
import com.example.heroesmusic.utils.ListMode;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class SingerSection extends Section implements FilterableSection {
    private List<Singer> mSingerList;
    private List<Singer> mAllSinger;
    private Context mContext;

    public SingerSection(List<Singer> singerList, Context context) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_singer)
                .headerResourceId(R.layout.row_section_header)
                .footerResourceId(R.layout.row_section_footer)
                .build());
        mSingerList = new ArrayList<>(singerList);
        mAllSinger = new ArrayList<>(singerList);
        mContext = context;
    }

    public List<Singer> getSingerList() {
        return mSingerList;
    }

    public void setSingerList(List<Singer> singerList) {
        mSingerList = singerList;
        mAllSinger = new ArrayList<>(singerList);
    }

    @Override
    public int getContentItemsTotal() {
        return Math.min(mSingerList.size(), 3);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new SingerAdapter.SingerHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        SingerAdapter.SingerHolder singerHolder= (SingerAdapter.SingerHolder) viewHolder;
        Singer singer = mSingerList.get(i);
        singerHolder.bind(singer, mContext);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view, ListMode.singer);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view, ListMode.singer);
    }

    @Override
    public void filter(@NonNull final String query) {
        if (TextUtils.isEmpty(query)) {
            mSingerList.clear();
            mSingerList.addAll(mAllSinger);
            this.setVisible(true);
        } else {
            mSingerList.clear();
            for (final Singer singer : mAllSinger) {
                if (singer.getSingerName().toLowerCase()
                        .contains(query.toLowerCase())) {
                    mSingerList.add(singer);
                }
            }
            this.setVisible(!mSingerList.isEmpty());
            setHasFooter(mSingerList.size() > 3);
        }
        setHasFooter(mSingerList.size() > 3);
    }
}
