package com.example.heroesmusic.utils;

import androidx.annotation.NonNull;

import com.example.heroesmusic.model.Music;

import java.util.List;

public interface FilterableSection {
    void filter(@NonNull final String query);
}
