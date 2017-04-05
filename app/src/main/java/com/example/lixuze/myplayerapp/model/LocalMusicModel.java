package com.example.lixuze.myplayerapp.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Files.FileColumns;

import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.db.AlbumInfoDao;
import com.example.lixuze.myplayerapp.db.ArtistInfoDao;
import com.example.lixuze.myplayerapp.db.FavoriteInfoDao;
import com.example.lixuze.myplayerapp.db.FolderInfoDao;
import com.example.lixuze.myplayerapp.db.MusicInfoDao;
import com.example.lixuze.myplayerapp.entites.Album_info;
import com.example.lixuze.myplayerapp.entites.Artist_info;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.entites.Music_info;
import com.example.lixuze.myplayerapp.model.IModel.ILocalMusicModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shinelon on 2017/2/17.
 */

public class LocalMusicModel implements ILocalMusicModel{
    private  static LocalMusicModel localMusicModel;
    private static String[] proj_artist = new String[]{
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS};
    private static String[] proj_folder = new String[]{MediaStore.Files.FileColumns.DATA};
    private static String[] proj_album = new String[]{Albums.ALBUM,
            Albums.NUMBER_OF_SONGS, Albums._ID, Albums.ALBUM_ART};


    public static final int FILTER_SIZE = 1 * 1024 * 1024;// 1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000;// 1分钟
    //本地音乐信息库
    private static MusicInfoDao musicinfoDao;
    // 歌手信息数据库
    private static ArtistInfoDao artistInfoDao;
    // 文件夹信息数据库
    private static FolderInfoDao folderInfoDao;
    // 专辑信息数据库
    private static AlbumInfoDao albumInfoDao;
    //我的最爱信息数据库
    private static FavoriteInfoDao favoriteInfoDao;

    public LocalMusicModel() {
    }

    public  static  LocalMusicModel getInstance(){
        if (localMusicModel==null){
            localMusicModel = new LocalMusicModel();
        }
        return localMusicModel;
    }

    @Override
    public List<Music_info> queryMusicFirst(Context context) {
        if (musicinfoDao == null) {
            musicinfoDao = new MusicInfoDao(context);
        }
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        StringBuffer select = new StringBuffer(" 1=1 " +
                " and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE +
                " and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION);
        List<Music_info> list = getMusicList(cr.query(uri, null, select.toString(), null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER));
        musicinfoDao.saveMusicInfo(list);
        return list;
    }

    @Override
    public List<Music_info> queryMusic(Context context) {
        if (musicinfoDao == null) {
            musicinfoDao = new MusicInfoDao(context);
        }
/*        if (musicinfoDao.hasData()){
            switch (from) {
                case Constants.START_FROM_LOCAL:
                    return musicinfoDao.getMusicInfo();
                case Constants.START_FROM_ARTIST:
                    if (musicinfoDao.hasData()) {
                        return musicinfoDao.getMusicInfoByType(selection,
                                Constants.START_FROM_ARTIST);
                    }
                case Constants.START_FROM_ALBUM:
                    if (musicinfoDao.hasData()) {
                        return musicinfoDao.getMusicInfoByType(selection,
                                Constants.START_FROM_ALBUM);
                    }
                case Constants.START_FROM_FOLDER:
                    if (musicinfoDao.hasData()) {
                        return musicinfoDao.getMusicInfoByType(selection, Constants.START_FROM_FOLDER);
                    }
                case Constants.START_FROM_FAVORITE:
                    if (musicinfoDao.hasData()) {
                        return musicinfoDao.getMusicInfoByType(selection,Constants. START_FROM_FAVORITE);
                    }
                default:
                    return null;
            }
        }else {
            return null;
        }*/
        if (musicinfoDao.hasData()){
            return musicinfoDao.getMusicInfo();
        }else {
            ContentResolver cr = context.getContentResolver();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            StringBuffer select = new StringBuffer(" 1=1 " +
                    " and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE +
                    " and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION);
            List<Music_info> list = getMusicList(cr.query(uri, null, select.toString(), null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER));
            musicinfoDao.saveMusicInfo(list);
            return list;
        }

    }

    @Override
    public List<Artist_info> queryArtist(Context context) {
        if (artistInfoDao == null) {
            artistInfoDao = new ArtistInfoDao(context);
        }
        if (artistInfoDao.hasData()) {
            return artistInfoDao.getArtistInfo();
        } else {
            Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
            ContentResolver cr = context.getContentResolver();
            List<Artist_info> list = getArtistList(cr.query(uri, null,
                    null, null, MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            artistInfoDao.saveArtistInfo(list);
            return list;
        }
    }

    @Override
    public List<Album_info> queryAlbums(Context context) {
        if (albumInfoDao == null) {
            albumInfoDao = new AlbumInfoDao(context);
        }
        if (albumInfoDao.hasData()) {
            return albumInfoDao.getAlbumInfo();
        } else {
            Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            ContentResolver cr = context.getContentResolver();
            StringBuilder where = new StringBuilder(MediaStore.Audio.Albums._ID
                    + " in (select distinct " + Media.ALBUM_ID
                    + " from audio_meta where (1=1 " + " and " + Media.SIZE + " > " + FILTER_SIZE
                    + " and " + Media.DURATION + " > " + FILTER_DURATION);
            where.append("))");
            // Media.ALBUM_KEY 按专辑名称排序
            List<Album_info> list = getAlbumList(cr.query(uri, proj_album,
                    null, null, Media.ALBUM_KEY));
            albumInfoDao.saveAlbumInfo(list);
            return list;
        }
    }

    @Override
    public List<Folder_info> queryFolder(Context context) {
        if (folderInfoDao == null) {
            folderInfoDao = new FolderInfoDao(context);
        }
        if (folderInfoDao.hasData()) {
            return folderInfoDao.getFolderInfo();
        } else {
            Uri uri = MediaStore.Files.getContentUri("external");
            ContentResolver cr = context.getContentResolver();
            StringBuilder mSelection = new StringBuilder(FileColumns.MEDIA_TYPE
                    + " = " + FileColumns.MEDIA_TYPE_AUDIO + " and " + "("
                    + FileColumns.DATA + " like'%.mp3' or " + Media.DATA
                    + " like'%.wma')" + " and " + Media.SIZE + " > " + FILTER_SIZE
                    + " and " + Media.DURATION + " > " + FILTER_DURATION);
            // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
            mSelection.append(") group by ( " + FileColumns.PARENT);
            List<Folder_info> list = getFolderList(cr.query(uri, proj_folder, mSelection.toString(), null, null));
            folderInfoDao.saveFolderInfo(list);
            return list;
        }
    }
    @Override
    public List<Music_info> getMusicbyType(int type,String selection){
        return musicinfoDao.getMusicInfoByType(type,
                selection);
    }

    private static List<Music_info> getMusicList(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ArrayList<Music_info> musicList = new ArrayList<Music_info>();
        while (cursor.moveToNext()) {
            Music_info music = new Music_info();
            int songId = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));
            int albumId = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            int duration = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));
            String musicName = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));
            String folderPath = filePath.substring(0,
                    filePath.lastIndexOf(File.separator));
            int favorite = 0;
            int musictab = 0;
            // String musicNameKey = StringHelper.getPingYin(music.musicName);
            //String artistKey = StringHelper.getPingYin(music.artist);
            music.setSongid(songId);
            music.setAlbumid(albumId);
            music.setDuration(duration);
            music.setMusicname(musicName);
            music.setArtist(artist);
            music.setUrl(filePath);
            music.setFolder(folderPath);
            music.setFavorite(favorite);
            music.setMusictab(musictab);
            // music.setMusicnamekey(musicNameKey);
            // music.setArtistkey(artistKey);
            musicList.add(music);
        }
        cursor.close();
        return musicList;
    }


    private static List<Artist_info> getArtistList(Cursor cursor) {
        List<Artist_info> list = new ArrayList<Artist_info>();
        while (cursor.moveToNext()) {
            Artist_info info = new Artist_info();
            String artist_name = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            int number_of_tracks = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            info.setArtist_name(artist_name);
            info.setNumber_of_tracks(number_of_tracks);
            list.add(info);
        }
        cursor.close();
        return list;
    }


    private static List<Folder_info> getFolderList(Cursor cursor) {
        List<Folder_info> list = new ArrayList<Folder_info>();
        while (cursor.moveToNext()) {
            Folder_info info = new Folder_info();
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA));
            String folder_path = filePath.substring(0,
                    filePath.lastIndexOf(File.separator));
            String folder_name = folder_path.substring(folder_path
                    .lastIndexOf(File.separator) + 1);
            info.setFolder_path(folder_path);
            info.setFolder_name(folder_name);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    private static List<Album_info> getAlbumList(Cursor cursor) {
        List<Album_info> list = new ArrayList<Album_info>();
        while (cursor.moveToNext()) {
            Album_info info = new Album_info();
            String album_name = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            int number_of_songs = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            String album_art = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            info.setAlbum_name(album_name);
            info.setAlbum_id(album_id);
            info.setNumber_of_songs(number_of_songs);
            info.setAlbum_art(album_art);
            list.add(info);
        }
        cursor.close();
        return list;
    }
}
