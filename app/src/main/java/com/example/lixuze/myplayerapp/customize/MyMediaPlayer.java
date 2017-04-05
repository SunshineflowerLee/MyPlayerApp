package com.example.lixuze.myplayerapp.customize;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by acer on 2016/2/16.
 */
public class MyMediaPlayer extends MediaPlayer {
    private  Context context;
    public MyMediaPlayer(Context context) {
        this.context=context;
    }

    @Override
    public void start() throws IllegalStateException {
        Intent intent_start=new Intent("intent_start");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent_start);
        super.start();
    }

    @Override
    public void pause() throws IllegalStateException {
        Intent intent_pause=new Intent("intent_pause");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent_pause);
        super.pause();
    }
}
