package com.example.lixuze.myplayerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.adapter.LocalRecyclerAdapter;
import com.example.lixuze.myplayerapp.adapter.QuickAdapter;
import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

import java.util.ArrayList;

/**
 * Created by lixuze on 16-12-21.
 */

public class ViewpagerArtistFragment extends Fragment {
    private final String TAG = "ViewpagerArtistFragment";
    private ILocalMusicPresenter presenter;
    private RecyclerView mRecyclerView;
    private LocalRecyclerAdapter localRecyclerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_pager2, null);
        presenter = LocalMusicPresenter.getInstance();
        presenter.setViewpagerArtistFragment(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.local_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        localRecyclerAdapter = new LocalRecyclerAdapter(Constants.START_FROM_ARTIST);
        mRecyclerView.setAdapter(localRecyclerAdapter);
        if (presenter.getMusicList() == null){
            mRecyclerView.setVisibility(View.GONE);
        }
        return view;
    }
    public void getMusicListFinish(){
        mRecyclerView.setVisibility(View.VISIBLE);
        localRecyclerAdapter.getListByType( Constants.START_FROM_ARTIST);
        localRecyclerAdapter.notifyDataSetChanged();
    }
}
