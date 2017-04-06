package com.example.lixuze.myplayerapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.View.IView.IActivity;
import com.example.lixuze.myplayerapp.adapter.QuickAdapter;
import com.example.lixuze.myplayerapp.adapter.entites.SongList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuze on 16-12-20.
 */

public class ViewpagerMainFragment1 extends Fragment implements View.OnClickListener {
    private ImageView imageView;
    private View toLocalMusic;
    private View toDownloadMusic;
    private View toRecentMusic;
    private View toFavoriteMusic;
    private View toFavoriteRadio;

    private IActivity onViewChangeListener;
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;
    private List<SongList> songLists;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_main1,null);

        /*scrollView = (NestedScrollView) view.findViewById(R.id.viewpager_main1_scrollView);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int scrollhigh = imageView.getHeight();
                onViewChangeListener.onScroll(scrollhigh,scrollY,oldScrollY);
                Log.d("scrollstate","  scrollhigh:"+scrollhigh+"  scrollY:"+scrollY+"  oldScrollY:"+oldScrollY);
            }
        });*/
        initview(view);
        return view;
    }

    private void initview(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_vp_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int y = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int scrollhigh = imageView.getHeight();
                y = y+dy;
                onViewChangeListener.onScroll(scrollhigh,y);
                Log.d("scrollstate","  scrollhigh:"+scrollhigh+"  scrollY:"+y);
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }


    private void initAdapter() {
        songLists = new ArrayList<>();
        mQuickAdapter = new QuickAdapter(songLists);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);
        View headerView = getHeaderView();
        View secondHeaderView = getSecondHeaderView();
        View footerView = getFooterView();
        mQuickAdapter.addHeaderView(headerView);
        mQuickAdapter.addHeaderView(secondHeaderView);
        mQuickAdapter.addFooterView(footerView);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {

            }
        });
    }


    private View getHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.viewpager_main1_header, null);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imageView = (ImageView) view.findViewById(R.id.viewpager_main1_imageview);
        toLocalMusic = view.findViewById(R.id.to_local_music);
        toDownloadMusic = view.findViewById(R.id.to_download_music);
        toRecentMusic = view.findViewById(R.id.to_recent_music);

        toLocalMusic.setOnClickListener(this);
        toDownloadMusic.setOnClickListener(this);
        toRecentMusic.setOnClickListener(this);
        Glide.with(getActivity()).load(R.mipmap.material_img).into(imageView);
        return view;
    }

    private View getSecondHeaderView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.viewpager_main1_secondheader, null);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        toFavoriteMusic = view.findViewById(R.id.to_favorite_music);
        toFavoriteRadio = view.findViewById(R.id.to_favorite_radio);
        toFavoriteMusic.setOnClickListener(this);
        toFavoriteRadio.setOnClickListener(this);
        return view;
    }

    private View getFooterView(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.viewpager_main1_footer, null);
        view.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongList songList = new SongList();
                songList.setSongListName("aa:");
                songLists.add(songList);
                mQuickAdapter.notifyItemInserted(songLists.size());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null){
            onViewChangeListener = (IActivity) context;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_local_music:
                LocalMusicFragment localMusicFragment = new LocalMusicFragment();
                onViewChangeListener.addFragement(this,localMusicFragment);
                break;
            case R.id.to_download_music:
                break;
            case R.id.to_recent_music:
                break;
            case R.id.to_favorite_music:
                break;
            case R.id.to_favorite_radio:
                break;
        }
    }
}
