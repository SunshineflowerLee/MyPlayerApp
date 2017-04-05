package com.example.lixuze.myplayerapp.persenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.entites.Album_info;
import com.example.lixuze.myplayerapp.entites.Artist_info;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.entites.Music_info;
import com.example.lixuze.myplayerapp.fragment.ViewpagerArtistFragment;
import com.example.lixuze.myplayerapp.fragment.ViewpagerFloderFragment;
import com.example.lixuze.myplayerapp.fragment.ViewpagerMusicFragment;
import com.example.lixuze.myplayerapp.model.LocalMusicModel;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.service.MusicService;

import java.util.List;



/**
 * Created by Shinelon on 2017/2/18.
 */

public class LocalMusicPresenter implements ILocalMusicPresenter {
    private final String TAG = "LocalMusicPresenter";
    private final int GET_ALL_LIST_FINISH = 0;
    private static ILocalMusicPresenter localMusicPersenter;
    private LocalMusicModel localMusicModel;
    private List<Music_info> music_list;
    private List<Artist_info> artist_list;
    private List<Album_info> album_list;
    private List<Folder_info> folder_list;
    private ViewpagerMusicFragment viewpagerMusicFragment;
    private ViewpagerArtistFragment viewpagerArtistFragment;
    private ViewpagerFloderFragment viewpagerFloderFragment;

    private MusicService musicService;
    private  Intent musicIntent;
    private Context context;
    private ServiceConnection conn ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_ALL_LIST_FINISH:
                    if (viewpagerMusicFragment!=null){
                        viewpagerMusicFragment.getMusicListFinish();
                        viewpagerArtistFragment.getMusicListFinish();
                        viewpagerFloderFragment.getMusicListFinish();
                        Log.d(TAG,artist_list.size()+"");
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };



    public LocalMusicPresenter() {
        localMusicModel = LocalMusicModel.getInstance();
    }

    public  static  ILocalMusicPresenter getInstance(){
        if (localMusicPersenter==null){
            localMusicPersenter = new LocalMusicPresenter();
        }
        return localMusicPersenter;
    }


    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setViewpagerMusicFragment(ViewpagerMusicFragment viewpagerMusicFragment) {
        this.viewpagerMusicFragment = viewpagerMusicFragment;
    }
    @Override
    public void setViewpagerArtistFragment(ViewpagerArtistFragment viewpagerArtistFragment) {
        this.viewpagerArtistFragment = viewpagerArtistFragment;
    }
    @Override
    public void setViewpagerFloderFragment(ViewpagerFloderFragment viewpagerFloderFragment) {
        this.viewpagerFloderFragment = viewpagerFloderFragment;
    }


    @Override
    public void bindService(){
        if (musicIntent == null){
            musicIntent = new Intent(context,MusicService.class);
        }
        conn =  new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // TODO Auto-generated method stub
                MusicService.MyBinder binder = (MusicService.MyBinder)service;
                musicService = binder.getService();
            }
        };
        Log.d(TAG,"绑定");
        context.bindService(musicIntent, conn, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void unBind(){
        context.stopService(musicIntent);
        context.unbindService(conn);
    }


    //获取所有音乐、歌手、专辑、文件夹
    @Override
    public void getAllList(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                music_list = localMusicModel.queryMusic(context);
                artist_list = localMusicModel.queryArtist(context);
                album_list = localMusicModel.queryAlbums(context);
                folder_list = localMusicModel.queryFolder(context);
                Message msg = new Message();
                msg.what = GET_ALL_LIST_FINISH;
                handler.sendMessage(msg);
                Log.d(TAG,"拿到数据");
            }
        }).start();
    }
    //根据不同的专辑歌手文件夹获取不同的list
    @Override
    public List<Music_info> getMusicListBytype(int type,String selection){
        if (type==Constants.START_FROM_LOCAL){
            return music_list;
        }
        return localMusicModel.getMusicbyType(type,selection);
    }
    @Override
    public List<Music_info> getMusicList() {
        return music_list;
    }

    @Override
    public List<Artist_info> getArtistList() {
        return artist_list;
    }

    @Override
    public List<Album_info> getAlbumList() {
        return album_list;
    }

    @Override
    public List<Folder_info> getFoloderList() {
        return folder_list;
    }


    //第一次播放音乐或者切歌
    @Override
    public void startMusic(List<Music_info> list,int position) {
       Music_info music_info = list.get(position);
        Log.d(TAG,musicService.getPosition()+"");
       if (musicService.getMusic_info()==null||!music_info.getUrl().equals(musicService.getMusic_info().getUrl())){
            musicService.setMusic_info(music_info);
            musicService.setList_music(list);
            musicService.setPosition(position);
            context.startService(musicIntent);
        }else if (musicService.isPlaying()){
           pauseMusic();
       }else {
           playMusic();
       }
    }
    //播放音乐
    @Override
    public void playMusic() {
        musicService.playMusic();;
    }
    //暂停音乐
    @Override
    public void pauseMusic() {
        musicService.pauseMusic();
    }
    //拉动进度
    @Override
    public void seekTo(int progress) {
        musicService.seekTo(progress);
    }

    @Override
    public void getProgress(int progress) {
        //获取进度后操作
    }

}
