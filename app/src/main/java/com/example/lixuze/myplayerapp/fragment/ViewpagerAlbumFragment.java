package com.example.lixuze.myplayerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lixuze.myplayerapp.R;

/**
 * Created by lixuze on 16-12-21.
 */

public class ViewpagerAlbumFragment extends Fragment {
    private final String TAG = "ViewpagerAlbumFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_pager1, null);
        return view;
    }
    public void getMusicListFinish(){

    }
}
