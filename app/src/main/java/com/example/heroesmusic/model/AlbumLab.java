package com.example.heroesmusic.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AlbumLab {

    private static AlbumLab mInstance;
    private List<Album> mAlbums;

    private AlbumLab(Context context){
        mAlbums = new ArrayList<>();
        mAlbums = getAlbums(MusicLab.getInstance(context).getMusicList());
    }

    public List<Album> getAlbums() {
        return mAlbums;
    }

    public static AlbumLab getInstance(Context context) {
        if (mInstance == null)
            return mInstance = new AlbumLab(context);
        return mInstance;
    }

    private List<Album> getAlbums(List<Music> music){
        List<Album> albums = new ArrayList<>();
        for (int i = 0 ; i < music.size() ; i++){
            if (i == 0){
                Album album = new Album();
                album.setAlbumName(music.get(i).getAlbum());
                album.setAlbumPath(music.get(i).getMusicPath());
                album.setAlbumId(music.get(i).getAlbumId());
                album.setSinger(music.get(i).getSinger());
                albums.add(album);
                continue;
            }
            for (int j = 0 ; j < albums.size() ; j++){
                if (music.get(i).getAlbum().equals(albums.get(j).getAlbumName()))
                    break;
                else if(albums.size()-1 == j && !music.get(i).getAlbum().equals(albums.get(j).getAlbumName())){
                    Album album = new Album();
                    album.setAlbumName(music.get(i).getAlbum());
                    album.setSinger(music.get(i).getSinger());
                    album.setAlbumPath(music.get(i).getMusicPath());
                    album.setAlbumId(music.get(i).getAlbumId());
                    albums.add(album);
                }
            }
        }
        return albums;
    }
}
