package com.example.heroesmusic.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.utils.ListMode;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextHeader;

    public HeaderViewHolder(@NonNull View itemView, ListMode listMode) {
        super(itemView);
        mTextHeader = itemView.findViewById(R.id.text_header);
        switch (listMode){
            case music:
                mTextHeader.setText(R.string.music);
                break;
            case singer:
                mTextHeader.setText(R.string.artist);
                break;
            case album:
                mTextHeader.setText(R.string.album);
                break;
        }
    }
}
