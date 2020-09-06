package com.example.heroesmusic.adapters;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.controller.MusicListActivity;
import com.example.heroesmusic.controller.PlayMusicActivity;
import com.example.heroesmusic.controller.SearchFragment;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.utils.ListMode;
import com.example.heroesmusic.utils.ShowMoreEvent;

import org.greenrobot.eventbus.EventBus;

public class FooterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextFooter;
    private ListMode mListMode;
    private boolean mClick;

    public FooterViewHolder(@NonNull View itemView, ListMode listMode) {
        super(itemView);
        itemView.setOnClickListener(this);
        mTextFooter = itemView.findViewById(R.id.footer_text);
        mListMode = listMode;
        mClick = false;
        switch (mListMode) {
            case music:
                mTextFooter.setText("See more music");
                break;
            case singer:
                mTextFooter.setText("See more artists");
                break;
            case album:
                mTextFooter.setText("See more albums");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new ShowMoreEvent(mListMode));
    }
}
