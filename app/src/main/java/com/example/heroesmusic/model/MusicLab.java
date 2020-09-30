package com.example.heroesmusic.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.heroesmusic.R;
import com.example.heroesmusic.database.AppDatabase;
import com.example.heroesmusic.database.FavoriteDataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MusicLab {

    private static MusicLab mInstance;
    private LinkedHashMap<Long, Music> mMusicList;
    private List<Music> mAllMusic;
    private Context mContext;

    private MusicLab(Context context) {
        mContext = context;
        mMusicList = new LinkedHashMap<>();
        mMusicList = getMusic(context);
    }

    public static MusicLab getInstance(Context context) {
        if (mInstance == null)
            return mInstance = new MusicLab(context);
        return mInstance;
    }

    public List<Music> getMusicList() {
        return new ArrayList<>(mMusicList.values());
    }

    public LinkedHashMap<Long, Music> getMusic(Context context){
        LinkedHashMap<Long, Music> musicList = new LinkedHashMap<>();
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION };
        final String where = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        final Cursor cursor = context.getContentResolver().query(uri,
                cursor_cols, where, null, null);
        while (cursor.moveToNext()) {
            String artist = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String track = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String data = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            Long albumId = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            long mySongId = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID));
            Music music = new Music();
            music.setSinger(artist);
            music.setAlbum(album);
            music.setMusicPath(data);
            music.setMusicName(track);
            music.setMusicId(mySongId);
            music.setAlbumId(albumId);
            musicList.put(music.getMusicId(), music);
        }
        return musicList;
    }

    public Bitmap getMusicBitmap(Music music){
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, music.getAlbumId());
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), albumArtUri);

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.default_music_cover);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public List<Music> getMusicWithName(String name){
        List<Music> allList = new ArrayList<>(mMusicList.values());
        List<Music> music = new ArrayList<>();
        if (name.equals("all music"))
            return allList;
        if (name.equals("favorite"))
            return getFavoriteMusic();
        for (int i = 0 ; i < allList.size() ; i++){
            if (allList.get(i).getSinger().equals(name) || allList.get(i).getAlbum().equals(name) ||
            allList.get(i).getMusicName().equalsIgnoreCase(name))
                music.add(allList.get(i));
        }
        if (music.size() == 0)
            music = filterMusic(name);
        return music;
    }

    public List<Music> getFavoriteMusic(){
        List<Music> musicList = new ArrayList<>();
        List<FavoriteDataSource> dataSources = new ArrayList<>(AppDatabase.getInstance(mContext).favoriteDao().getAll());
        for (int i = 0; i < dataSources.size(); i++){
            musicList.add(MusicLab.getInstance(mContext).getMusicById(dataSources.get(i).getFavoriteId()));
        }
        return musicList;
    }

    public Music getMusicById(long musicId){
        return mMusicList.get(musicId);
    }

    public List<Music> filterMusic(@NonNull String query) {
        List<Music> mFilterMusic = new ArrayList<>();
        mAllMusic = new ArrayList<>(mMusicList.values());
        if (TextUtils.isEmpty(query)) {
            mFilterMusic.clear();
            mFilterMusic.addAll(mAllMusic);
        } else {
            mFilterMusic.clear();
            for (final Music music : mAllMusic) {
                if (music.getMusicName().toLowerCase()
                        .contains(query.toLowerCase())) {
                    mFilterMusic.add(music);
                }
            }
        }
        return mFilterMusic;
    }
}