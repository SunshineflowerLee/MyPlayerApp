package com.example.lixuze.myplayerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.lixuze.myplayerapp.entites.Album_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/25.
 */
public class AlbumInfoDao {
    private static final String TABLE_ALBUM = "album_info";
    private Context context;

    public AlbumInfoDao(Context context) {
        this.context = context;
    }

    public void saveAlbumInfo(List<Album_info> list) {
        SQLiteDatabase db = DatabaseHelper.getInstance(context);
        for (Album_info info : list) {
            ContentValues cv = new ContentValues();
            cv.put("album_name", info.getAlbum_name());
            cv.put("album_id", info.getAlbum_id());
            cv.put("number_of_songs", info.getNumber_of_songs());
            cv.put("album_art", info.getAlbum_art());
            db.insert(TABLE_ALBUM, null, cv);
        }
    }

    public List<Album_info> getAlbumInfo() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context);
        List<Album_info> list = new ArrayList<Album_info>();
        String sql = "select * from " + TABLE_ALBUM;
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()) {
            Album_info info = new Album_info();
            String album_name = cursor.getString(cursor.getColumnIndex("album_name"));
            String album_art = cursor.getString(cursor.getColumnIndex("album_art"));
            int album_id = cursor.getInt(cursor.getColumnIndex("album_id"));
            int number_of_songs = cursor.getInt(cursor.getColumnIndex("number_of_songs"));
            info.setAlbum_name(album_name);
            info.setAlbum_art(album_art);
            info.setAlbum_id(album_id);
            info.setNumber_of_songs(number_of_songs);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    /**
     * 数据库中是否有数据
     * @return
     */
    public boolean hasData() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context);
        String sql = "select count(*) from " + TABLE_ALBUM;
        Cursor cursor = db.rawQuery(sql, null);
        boolean has = false;
        if(cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if(count > 0) {
                has = true;
            }
        }
        cursor.close();
        return has;
    }

    public int getDataCount() {
        SQLiteDatabase db = DatabaseHelper.getInstance(context);
        String sql = "select count(*) from " + TABLE_ALBUM;
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

}
