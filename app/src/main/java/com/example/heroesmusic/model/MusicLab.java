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
import com.example.heroesmusic.utils.FilterableSection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicLab {

    private static MusicLab mInstance;
    private List<Music> mMusicList;
    private List<Music> mAllMusic;
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

    public List<Music> getMusic(Context context){
        List<Music> musicList = new ArrayList<>();
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

    public List<Music> getMusicWithName(String name){
        List<Music> music = new ArrayList<>();
        if (name.equals("all music"))
            return mMusicList;
        for (int i = 0 ; i < mMusicList.size() ; i++){
            if (mMusicList.get(i).getSinger().equals(name) || mMusicList.get(i).getAlbum().equals(name) ||
            mMusicList.get(i).getMusicName().equalsIgnoreCase(name))
                music.add(mMusicList.get(i));
        }
        if (music.size() == 0)
            music = filterMusic(name);
        return music;
    }

    public List<Music> filterMusic(@NonNull String query) {
        List<Music> mFilterMusic = new ArrayList<>();
        mAllMusic = new ArrayList<>(mMusicList);
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
