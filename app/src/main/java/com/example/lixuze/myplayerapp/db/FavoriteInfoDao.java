package com.example.lixuze.myplayerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.lixuze.myplayerapp.entites.Favorite_info;
import com.example.lixuze.myplayerapp.entites.Music_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/31.
 */
public class FavoriteInfoDao {
    private Context mContext;

    private static final String TABLE_FAVORITE = "Favorite_info";

    public FavoriteInfoDao(Context mContext) {
        this.mContext = mContext;
    }


    /*在我的最爱列表加入音乐*/
    public void saveFavoriteInfo(Music_info music_info) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        ContentValues cv = new ContentValues();
        cv.put("songid", music_info.getSongid());
        cv.put("albumid", music_info.getAlbumid());
        cv.put("duration", music_info.getDuration());
        cv.put("musicname", music_info.getMusicname());
        cv.put("artist", music_info.getArtist());
        cv.put("data", music_info.getUrl());
        cv.put("folder", music_info.getFolder());
        cv.put("musicnamekey", music_info.getMusicnamekey());
        cv.put("artistkey", music_info.getArtistkey());
        cv.put("favorite", music_info.getFavorite());
        cv.put("musictab", music_info.getMusictab());
        db.insert(TABLE_FAVORITE, null, cv);
    }
   /*在我的最爱列表删除音乐*/

    public void deleteFavorite(String url) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        db.delete(TABLE_FAVORITE, "data=?", new String[]{url});
    }


    /*
* 得到数据库歌曲的信息
* */
    public List<Favorite_info> getFavoriteInfo() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select * from " + TABLE_FAVORITE;
        return parseCursor(db.rawQuery(sql, null));
    }

    private List<Favorite_info> parseCursor(Cursor cursor) {
        List<Favorite_info> list = new ArrayList<Favorite_info>();
        while (cursor.moveToNext()) {
            Favorite_info music = new Favorite_info();
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int Songid = cursor.getInt(cursor.getColumnIndex("songid"));
            int albumId = cursor.getInt(cursor.getColumnIndex("albumid"));
            int duration = cursor.getInt(cursor.getColumnIndex("duration"));
            String musicName = cursor.getString(cursor.getColumnIndex("musicname"));
            String artist = cursor.getString(cursor.getColumnIndex("artist"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            String folder = cursor.getString(cursor.getColumnIndex("folder"));
            String musicNameKey = cursor.getString(cursor.getColumnIndex("musicnamekey"));
            String artistKey = cursor.getString(cursor.getColumnIndex("artistkey"));
            int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
            int musictab = cursor.getInt(cursor.getColumnIndex("playtab"));

            music.setSongid(Songid);
            music.setAlbumid(albumId);
            music.setDuration(duration);
            music.setMusicname(musicName);
            music.setArtist(artist);
            music.setUrl(data);
            music.setFolder(folder);
            music.setMusicnamekey(musicNameKey);
            music.setArtistkey(artistKey);
            music.setFavorite(favorite);
            music.setMusictab(musictab);
            list.add(music);
        }
        cursor.close();
        return list;
    }


    /*更新音乐是否为最爱*/
    public void updataLove(String url, int arg) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        ContentValues cv = new ContentValues();
        cv.put("favorite", arg);
        db.update(TABLE_FAVORITE, cv, "data=?", new String[]{url});
    }


    /**
     * 数据库中是否有数据
     *
     * @return
     */
    public boolean hasData() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select count(*) from " + TABLE_FAVORITE;
        Cursor cursor = db.rawQuery(sql, null);
        boolean has = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                has = true;
            }
        }
        cursor.close();
        return has;
    }

    public int getDataCount() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select count(*) from " + TABLE_FAVORITE;
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}
