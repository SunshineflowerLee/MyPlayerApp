package com.example.lixuze.myplayerapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.adapter.MusicExpandListadapter;
import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuze on 16-12-17.
 */

public class LocalMusicChildFragment extends BaseFragment implements View.OnClickListener{
    private final String TAG = "LocalMusicChildFragment";
    private ExpandableListView expandableListView;
    private MusicExpandListadapter musicExpandListadapter;
    private ImageView back;
    private ImageView addMore;
    private TopRightMenu mTopRightMenu;
    private View view;
    private ILocalMusicPresenter presenter;
    private int type;
    private String selection;
    private String titlename;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_local_music_child,null);
        presenter = LocalMusicPresenter.getInstance();
        if (getArguments()!=null){
            type = getArguments().getInt("type");
            selection = getArguments().getString("selection");
            titlename = getArguments().getString("titlename");
        }
        Log.d(TAG,type+"   "+selection+"    "+titlename);
        initview(view);
/*        if (presenter.getMusicList()==null){
            presenter.getAllList(getContext());
        }*/
        return view;
    }
    private void initview(View view){
        back = (ImageView) view.findViewById(R.id.local_music_child_back);
        addMore = (ImageView) view.findViewById(R.id.local_music_child_more);
        expandableListView = (ExpandableListView) view.findViewById(R.id.child_expand_list);
        musicExpandListadapter = new MusicExpandListadapter(getActivity(), expandableListView, type ,selection);
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

        addMore.setOnClickListener(this);
        back.setOnClickListener(this);


        mTopRightMenu = new TopRightMenu(getActivity());
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("发起多人聊天"));
        menuItems.add(new MenuItem("加好友"));
        menuItems.add(new MenuItem("扫一扫"));
        mTopRightMenu
                .setHeight(480)     //默认高度480
                .setWidth(320)      //默认宽度wrap_content
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(false)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {

                    }
                });

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.local_music_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.local_music_more:
                mTopRightMenu.showAsDropDown(addMore, -225, 0);  //带偏移量
                break;
        }
    }

}
