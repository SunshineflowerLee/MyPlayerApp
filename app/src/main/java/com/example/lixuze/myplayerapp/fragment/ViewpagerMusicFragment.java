package com.example.lixuze.myplayerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.adapter.MusicExpandListadapter;
import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

/**
 * Created by lixuze on 16-12-21.
 */

public class ViewpagerMusicFragment extends Fragment {
    private final String TAG = "ViewpagerMusicFragment";
    private ExpandableListView expandableListView;
    private MusicExpandListadapter musicExpandListadapter;
    private ILocalMusicPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music_pager1, null);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expand_list);
        presenter = LocalMusicPresenter.getInstance();
        presenter.setViewpagerMusicFragment(this);
        musicExpandListadapter = new MusicExpandListadapter(getActivity(), expandableListView, Constants.START_FROM_LOCAL, null);
        expandableListView.setAdapter(musicExpandListadapter);
        /*设置只能展开一个子列表*/
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < musicExpandListadapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        if (presenter.getMusicList() == null){
            expandableListView.setVisibility(View.GONE);
        }
        return view;
    }
    public void getMusicListFinish(){
        Log.d(TAG,"获取数据");
        expandableListView.setVisibility(View.VISIBLE);
        musicExpandListadapter.getListByType( Constants.START_FROM_LOCAL,null);
        musicExpandListadapter.notifyDataSetChanged();
    }
}
