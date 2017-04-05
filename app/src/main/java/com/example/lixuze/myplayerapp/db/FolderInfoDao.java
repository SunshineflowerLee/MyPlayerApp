package com.example.lixuze.myplayerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lixuze.myplayerapp.entites.Folder_info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/24.
 */
public class FolderInfoDao {

    private static final String TABLE_FOLDER = "folder_info";
    private Context mContext;

    public FolderInfoDao(Context context) {
        this.mContext = context;
    }

    public void saveFolderInfo(List<Folder_info> list) {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        for (Folder_info info : list) {
            ContentValues cv = new ContentValues();
            cv.put("folder_name", info.getFolder_name());
            cv.put("folder_path", info.getFolder_path());
            db.insert(TABLE_FOLDER, null, cv);
        }
    }

    public List<Folder_info> getFolderInfo() {
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
        List<Folder_info> list = new ArrayList<Folder_info>();
        String sql = "select * from " + TABLE_FOLDER;
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()) {
            Folder_info info = new Folder_info();
            String folder_name = cursor.getString(cursor.getColumnIndex("folder_name"));
            String folder_path = cursor.getString(cursor.getColumnIndex("folder_path"));
            info.setFolder_name(folder_name);
            info.setFolder_path(folder_path);
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
        String sql = "select count(*) from " + TABLE_FOLDER;
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
        String sql = "select count(*) from " + TABLE_FOLDER;
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }
}
