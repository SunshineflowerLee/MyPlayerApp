package com.example.lixuze.myplayerapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.example.lixuze.myplayerapp.customize.MyMediaPlayer;
import com.example.lixuze.myplayerapp.entites.Music_info;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by acer on 2016/1/28.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnSeekCompleteListener{
    private final String TAG = "MusicService";
    private ILocalMusicPresenter presenter;
    private Music_info music_info;
    private List<Music_info> list_music;
    private int position;
    private int progress;
    private MySeekBar mySeekBar;
    private MyMediaPlayer mediaPlayer;

    /*在onCreate中初始化Music_info*/
    @Override
    public void onCreate() {
        Log.d(TAG,"MusicService.onCreate");
        presenter = LocalMusicPresenter.getInstance();
        initialize();
        super.onCreate();
    }

    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Nullable
    @Override
/*onBind只第一次绑定时才会走，onCreate也是*/
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"MusicService.onStartCommand");
        if (list_music.size() != 0){
            start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();

        super.onDestroy();
    }
    //该函数用来初始化播放器，有播放过就显示上次播放的最后一首音乐，没有就初始化为空白
    private void initialize(){
        mediaPlayer = new MyMediaPlayer(this.getApplicationContext());
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        music_info = new Music_info();
      /*  progress = 0;
       if ((list_music.size() == 0)|(list_tab.size()==0)) {
            music_info = new Music_info();
            music_info.setAlbumid(0);
            music_info.setDuration(0);
            music_info.setMusicname("");
            music_info.setArtist("");
            music_info.setUrl("");
            music_info.setFolder("");
            music_info.setFavorite(0);
            music_info.setMusicnamekey("");
            music_info.setArtistkey("");
            music_info.setMusictab(0);
            list_music = new ArrayList<>();
            list_music.add(music_info);
            position = 0;
       }else {
            position = list_tab.get(0)-1;
            music_info=list_music.get(position);
        }

        try {
            mediaPlayer.setDataSource(list_music.get(position).getUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    //该函数用来启动播放器播放
    private void start() {
        if (mediaPlayer == null) {
            mediaPlayer = new MyMediaPlayer(this.getApplicationContext());
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
        }
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(list_music.get(position).getUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playMusic();
    }

    //该函数发送广播，把歌曲信息发送出去
    private void sendDataBroadcast() {
        Intent intent_sendData = new Intent("music_data");
        intent_sendData.putExtra("music_data", music_info);
        intent_sendData.putExtra("position", position);
        intent_sendData.putExtra("list_music", (Serializable) list_music);
        LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(intent_sendData);
    }


    public void playMusic() {
        mediaPlayer.start();
        mySeekBar = new MySeekBar();
        mySeekBar.start();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public Music_info getMusic_info() {
        return music_info;
    }

    public void setMusic_info(Music_info music_info) {
        this.music_info = music_info;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Music_info> getList_music() {
        return list_music;
    }

    public void setList_music(List<Music_info> list_music) {
        this.list_music = list_music;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (position < list_music.size()) {
            position++;
            start();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    /*开启子线程来实时传递歌曲进度*/
    class MySeekBar extends Thread {
        @Override
        public void run() {
            super.run();
            while (mediaPlayer.isPlaying()) {
                progress = mediaPlayer.getCurrentPosition();
                presenter.getProgress(progress);
                try {
//                  休眠一秒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
