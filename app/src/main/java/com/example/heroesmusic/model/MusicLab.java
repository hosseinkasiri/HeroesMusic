package com.example.heroesmusic.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.heroesmusic.R;
import com.example.heroesmusic.helper.PictureUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION };
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
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
            int duration = cursor.getInt(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), albumArtUri);

            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.default_music_cover);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Music music = new Music();
            music.setSinger(artist);
            music.setAlbumArtBitmap(bitmap);
            music.setAlbum(album);
            music.setMusicPath(data);
            music.setMusicName(track);
            music.setAlbumArtUri(albumArtUri);
            music.setDuration(duration);
            musicList.add(music);
        }
        return musicList;
    }

    public static List<Singer> getSingers(List<Music> music){
        List<Singer> singers = new ArrayList<>();
        for (int i = 0 ; i < music.size() ; i++){
            if (i == 0){
                Singer singer = new Singer();
                singer.setSingerName(music.get(i).getSinger());
                singer.setPath(music.get(i).getMusicPath());
                singer.setSingerAlbumBitmap(music.get(i).getAlbumArtBitmap());
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
                    singer.setSingerAlbumBitmap(music.get(i).getAlbumArtBitmap());
                    singers.add(singer);
                }
            }
        }
        return singers;
    }

    public static List<Album> getAlbums(List<Music> music){
        List<Album> albums = new ArrayList<>();
        for (int i = 0 ; i < music.size() ; i++){
            if (i == 0){
                Album album = new Album();
                album.setAlbumName(music.get(i).getAlbum());
                album.setAlbumPath(music.get(i).getMusicPath());
                album.setAlbumBitmap(music.get(i).getAlbumArtBitmap());
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
                    album.setAlbumBitmap(music.get(i).getAlbumArtBitmap());
                    albums.add(album);
                }
            }
        }
        return albums;
    }
}
