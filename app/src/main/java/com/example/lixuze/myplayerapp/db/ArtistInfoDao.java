package com.example.lixuze.myplayerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.lixuze.myplayerapp.entites.Artist_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/24.
 */
public class ArtistInfoDao {
    private static final String TABLE_ARTIST = "artist_info";
    private Context mContext;

    public ArtistInfoDao(Context context) {
        this.mContext = context;
    }
    public void saveArtistInfo(List<Artist_info> list) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        for (Artist_info info : list) {
            ContentValues cv = new ContentValues();
            cv.put("artist_name", info.getArtist_name());
            cv.put("number_of_tracks", info.getNumber_of_tracks());
            db.insert(TABLE_ARTIST, null, cv);
        }
    }

    public List<Artist_info> getArtistInfo() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        List<Artist_info> list = new ArrayList<Artist_info>();
        String sql = "select * from " + TABLE_ARTIST;
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()) {
            Artist_info info = new Artist_info();
            String artist_name = cursor.getString(cursor.getColumnIndex("artist_name"));
            int number_of_tracks = cursor.getInt(cursor.getColumnIndex("number_of_tracks"));
            info.setArtist_name(artist_name);
            info.setNumber_of_tracks(number_of_tracks);
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
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select count(*) from " + TABLE_ARTIST;
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
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select count(*) from " + TABLE_ARTIST;
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}

