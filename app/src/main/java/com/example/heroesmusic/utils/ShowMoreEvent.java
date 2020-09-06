package com.example.heroesmusic.utils;

public class ShowMoreEvent {

    private ListMode mListMode;

    public ShowMoreEvent(ListMode listMode) {
        mListMode = listMode;
    }

    public ListMode getListMode() {
        return mListMode;
    }
}
