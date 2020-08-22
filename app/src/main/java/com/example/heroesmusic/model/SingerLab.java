package com.example.heroesmusic.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class SingerLab {

    private static SingerLab mInstance;
    private List<Singer> mSingers;

    private SingerLab(Context context) {
        mSingers = new ArrayList<>();
        mSingers = getSingers(MusicLab.getInstance(context).getMusicList());
    }

    public static SingerLab getInstance(Context context) {
        if (mInstance == null)
            return mInstance = new SingerLab(context);
        return mInstance;
    }

    public List<Singer> getSingers() {
        return mSingers;
    }

    private List<Singer> getSingers(List<Music> music){
        List<Singer> singers = new ArrayList<>();
        for (int i = 0 ; i < music.size() ; i++){
            if (i == 0){
                Singer singer = new Singer();
                singer.setSingerName(music.get(i).getSinger());
                singer.setPath(music.get(i).getMusicPath());
                singer.setAlbumId(music.get(i).getAlbumId());
                singers.add(singer);
                continue;
            }
            for (int j = 0 ; j < singers.size() ; j++){
                if (music.get(i).getSinger().equals(singers.get(j).getSingerName()))
                    break;
                else if(singers.size()-1 == j && !music.get(i).getSinger().equals(singers.get(j).getSingerName())){
                    Singer singer = new Singer();
                    singer.setSingerName(music.get(i).getSinger());
                    singer.setPath(music.get(i).getMusicPath());
                    singer.setAlbumId(music.get(i).getAlbumId());
                    singers.add(singer);
                }
            }
        }
        return singers;
    }
}
