package com.example.lixuze.myplayerapp.model.IModel;

import android.content.Context;

import com.example.lixuze.myplayerapp.entites.Album_info;
import com.example.lixuze.myplayerapp.entites.Artist_info;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.entites.Music_info;

import java.util.List;

/**
 * Created by Shinelon on 2017/2/17.
 */

public interface ILocalMusicModel {
    public List<Music_info> queryMusicFirst(Context context);
    public List<Music_info> queryMusic(Context context);
    public List<Artist_info> queryArtist(Context context);
    public List<Album_info> queryAlbums(Context context);
    public List<Folder_info> queryFolder(Context context);
    public List<Music_info> getMusicbyType(int type,String selection);
/*    public List<Music_info> getMusicListFromArtist(List<Music_info> list);
    public List<Music_info> getMusicListFromAlbum(List<Music_info> list);
    public List<Music_info> getMusicListFromFoloder(List<Music_info> list);
    public List<Music_info> getMusicListFromFavor(List<Music_info> list);*/
}
