package com.example.heroesmusic.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicLab {
    private List<Music> mMusic;

    public MusicLab() {
        mMusic = new ArrayList<>();
    }

    public List<Music> getMusic() {
        return mMusic;
    }
}
