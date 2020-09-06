package com.example.heroesmusic.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearSmoothScroller;

import com.example.heroesmusic.R;
import com.example.heroesmusic.helper.PictureUtils;
import com.example.heroesmusic.utils.ListMode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicLab {

    private static MusicLab mInstance;
    private List<Music> mMusicList;
    private Context mContext;

    private MusicLab(Context context) {
        mContext = context;
        mMusicList = new ArrayList<>();
        mMusicList = getMusic(context);
    }

    public static MusicLab getInstance(Context context) {
        if (mInstance == null)
            return mInstance = new MusicLab(context);
        return mInstance;
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    private List<Music> getMusic(Context context){
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
            long mySongId = cursor.getLong(cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID));
            Uri mySongUri=ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mySongId);
            Music music = new Music();
            music.setSinger(artist);
            music.setAlbum(album);
            music.setMusicPath(data);
            music.setMusicName(track);
            music.setMusicUri(mySongUri);
            music.setAlbumId(albumId);
            musicList.add(music);
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

    public List<Music> getMusic(String name){
        List<Music> music = new ArrayList<>();
        for (int i = 0 ; i < mMusicList.size() ; i++){
            if (mMusicList.get(i).getSinger().equals(name) || mMusicList.get(i).getAlbum().equals(name))
                music.add(mMusicList.get(i));
        }
        return music;
    }
}
