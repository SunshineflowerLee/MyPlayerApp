package com.example.lixuze.myplayerapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.adapter.LocalRecyclerAdapter;
import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

/**
 * Created by lixuze on 16-12-21.
 */

public class ViewpagerFloderFragment extends Fragment implements LocalRecyclerAdapter.OnItemClickListener{
    private final String TAG = "ViewpagerFloderFragment";
    private IActivity activity;
    private ILocalMusicPresenter presenter;
    private RecyclerView mRecyclerView;
    private LocalRecyclerAdapter localRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_pager2, null);
        presenter = LocalMusicPresenter.getInstance();
        presenter.setViewpagerFloderFragment(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.local_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        localRecyclerAdapter = new LocalRecyclerAdapter(Constants.START_FROM_FOLDER);
        localRecyclerAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(localRecyclerAdapter);
        if (presenter.getMusicList() == null){
            mRecyclerView.setVisibility(View.GONE);
        }
        return view;
    }
    public void getMusicListFinish(){
        mRecyclerView.setVisibility(View.VISIBLE);
        localRecyclerAdapter.getListByType( Constants.START_FROM_FOLDER);
        localRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null){
            activity = (IActivity) context;
        }
    }


    @Override
    public void onItemClick(View item, int position) {

        Folder_info folder_info=presenter.getFoloderList().get(position);
        LocalMusicChildFragment fragment_child=new LocalMusicChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",Constants.START_FROM_FOLDER);
        bundle.putString("selection", folder_info.getFolder_path());
        bundle.putString("titlename",folder_info.getFolder_name());
        fragment_child.setArguments(bundle);

        activity.addFragement(this,fragment_child);
    }
}
