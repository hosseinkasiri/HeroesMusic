package com.example.heroesmusic.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicLab {

    private static final int REQ_PERMISSION = 0;

    public static void getPermission(Context context){

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions((Activity) context,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , REQ_PERMISSION);
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);
            }
        }else {
            doStuff(context);
        }
    }

    public static void doStuff(Context context){
        getMusic(context);
    }

    public static List<Music> getMusic(Context context){
        List<Music> musicList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor musicCursor = contentResolver.query(musicUri,null, selection,null,null);
        if (musicCursor != null && musicCursor.moveToFirst()){
            int songTitle = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songPath = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            if (musicCursor != null) {
                if (musicCursor.moveToFirst()) {

                    do {
                        String currentTitle = musicCursor.getString(songTitle);
                        String currentArtist = musicCursor.getString(songArtist);
                        String currentAlbum = musicCursor.getString(songAlbum);
                        String currentPath = musicCursor.getString(songPath);
                        Music music = new Music();
                        music.setMusicName(currentTitle);
                        music.setSinger(currentArtist);
                        music.setAlbum(currentAlbum);
                        music.setMusicPath(currentPath);
                        musicList.add(music);

                    } while (musicCursor.moveToNext());
                }
                musicCursor.close();
            }
        }
        return musicList;
    }
}
