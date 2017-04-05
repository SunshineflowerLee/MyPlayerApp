package com.example.lixuze.myplayerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.entites.Music_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/20.
 */
public class MusicInfoDao {

    private static final String TABLE_MUSIC = "music_info";
/*    private static final String START_FROM_ARTIST = "start_from_artist";
    private static final String START_FROM_ALBUM = "start_from_album";
    private static final String START_FROM_FOLDER = "start_from_folder";
    private static final String START_FROM_FAVORITE = "start_from_favorite";*/
    private Context mContext;

    public MusicInfoDao(Context context) {
        this.mContext = context;
    }

    /*保存音乐数据*/
    public void saveMusicInfo(List<Music_info> list) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        for (Music_info music : list) {
            ContentValues cv = new ContentValues();
            cv.put("songid", music.getSongid());
            cv.put("albumid", music.getAlbumid());
            cv.put("duration", music.getDuration());
            cv.put("musicname", music.getMusicname());
            cv.put("artist", music.getArtist());
            cv.put("data", music.getUrl());
            cv.put("folder", music.getFolder());
            cv.put("musicnamekey", music.getMusicnamekey());
            cv.put("artistkey", music.getArtistkey());
            cv.put("favorite", music.getFavorite());
            cv.put("musictab", music.getMusictab());
            db.insert(TABLE_MUSIC, null, cv);
        }
    }

    /*扫描时逐个判断有新的数据就添加进去*/
    public void saveMusic(Music_info music) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        ContentValues cv = new ContentValues();
        cv.put("songid", music.getSongid());
        cv.put("albumid", music.getAlbumid());
        cv.put("duration", music.getDuration());
        cv.put("musicname", music.getMusicname());
        cv.put("artist", music.getArtist());
        cv.put("data", music.getUrl());
        cv.put("folder", music.getFolder());
        cv.put("musicnamekey", music.getMusicnamekey());
        cv.put("artistkey", music.getArtistkey());
        cv.put("favorite", music.getFavorite());
        cv.put("musictab", music.getMusictab());
        db.insert(TABLE_MUSIC, null, cv);
    }


    /*
    * 得到数据库歌曲的信息
    * */
    public List<Music_info> getMusicInfo() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select * from " + TABLE_MUSIC;
        return parseCursor(db.rawQuery(sql, null));
    }

    private List<Music_info> parseCursor(Cursor cursor) {
        List<Music_info> list = new ArrayList<Music_info>();
        while (cursor.moveToNext()) {
            Music_info music = new Music_info();
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));
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
            int musictab = cursor.getInt(cursor.getColumnIndex("musictab"));

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

    //根据不同的界面进入的歌曲界面选择歌曲
    public List<Music_info> getMusicInfoByType(int type,String selection) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "";
        if (type == Constants.START_FROM_ARTIST) {
            sql = "select * from " + TABLE_MUSIC + " where artist = ?";
        } else if (type == Constants.START_FROM_ALBUM) {
            sql = "select * from " + TABLE_MUSIC + " where albumid = ?";
        } else if (type == Constants.START_FROM_FOLDER) {
            sql = "select * from " + TABLE_MUSIC + " where folder = ?";
        } else if (type == Constants.START_FROM_FAVORITE) {
            sql = "select * from " + TABLE_MUSIC + " where favorite = ?";
        }
        return parseCursor(db.rawQuery(sql, new String[]{selection}));
    }

    /*更新音乐是否为最爱*/
    public void updataLove(String url, int arg) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        ContentValues cv = new ContentValues();
        cv.put("favorite", arg);
        db.update(TABLE_MUSIC, cv, "data=?", new String[]{url});
    }
    /*删除音乐*/
    public void delete_musicinfo(String url){
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        db.delete(TABLE_MUSIC, "data=?", new String[]{url});
    }
    /*该函数用来得到上次播放的音乐标记*/
    public List<Integer> get_musictab(){
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select _id from " + TABLE_MUSIC + " where musictab = 1";
        Cursor cursor = db.rawQuery(sql, null);
        List<Integer> music_id=new ArrayList<Integer>();
        while (cursor.moveToNext()){
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            music_id.add(_id);
        }
        cursor.close();
        return music_id;
    }
    /*为音乐加标记，以便再次开启播放器时可以显示上次最后播放的音乐*/
    public void updata_musictab(String selection,String[] url,int arg) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        ContentValues cv = new ContentValues();
        cv.put("musictab", arg);
        db.update(TABLE_MUSIC, cv, selection, url);
    }



    /**
     * 数据库中是否有数据
     *
     * @return
     */
    public boolean hasData() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        String sql = "select count(*) from " + TABLE_MUSIC;
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
        String sql = "select count(*) from " + TABLE_MUSIC;
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}
