package com.example.lixuze.myplayerapp.persenter.IPersenter;

import android.content.Context;

import com.example.lixuze.myplayerapp.entites.Album_info;
import com.example.lixuze.myplayerapp.entites.Artist_info;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.entites.Music_info;
import com.example.lixuze.myplayerapp.fragment.ViewpagerArtistFragment;
import com.example.lixuze.myplayerapp.fragment.ViewpagerFloderFragment;
import com.example.lixuze.myplayerapp.fragment.ViewpagerMusicFragment;

import java.util.List;

/**
 * Created by Shinelon on 2017/2/18.
 */

public interface ILocalMusicPresenter {
    public void setContext(Context context);
    public void setViewpagerMusicFragment (ViewpagerMusicFragment viewpagerMusicFragment);
    public void setViewpagerArtistFragment(ViewpagerArtistFragment viewpagerArtistFragment);
    public void setViewpagerFloderFragment(ViewpagerFloderFragment viewpagerFloderFragment);
    public void bindService();
    public void unBind();
    public void getAllList(Context context);
    public List<Music_info> getMusicListBytype(int type,String selection);
    public List<Music_info> getMusicList();
    public List<Artist_info> getArtistList();
    public List<Album_info> getAlbumList();
    public List<Folder_info> getFoloderList();
    public void startMusic(List<Music_info> list,int position);
    public void playMusic();
    public void pauseMusic();
    public void seekTo(int progress);
    public void getProgress(int progress);
}
