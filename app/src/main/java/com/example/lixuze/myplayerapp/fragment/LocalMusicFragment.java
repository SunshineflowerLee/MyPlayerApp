package com.example.lixuze.myplayerapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuze on 16-12-17.
 */

public class LocalMusicFragment extends BaseFragment implements View.OnClickListener{
    private final String TAG = "LocalMusicFragment";
    private ViewPager viewPager;
    private TopRightMenu mTopRightMenu;
    private TabLayout tabLayout;
    private View view;
    private ILocalMusicPresenter presenter;
    private ImageView addMore;
    private ImageView back;
    private List<Fragment> fragment_list;
    private String[] tab_name = {"歌曲", "歌手", "专辑","文件夹"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_local_music,null);
        presenter = LocalMusicPresenter.getInstance();
        initview(view);
        if (presenter.getMusicList()==null){
            presenter.getAllList(getContext());
        }
        return view;
    }
    private void initview(View view){
        back = (ImageView) view.findViewById(R.id.local_music_back);
        addMore = (ImageView) view.findViewById(R.id.local_music_more);
        viewPager = (ViewPager) view.findViewById(R.id.local_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.local_music_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setOffscreenPageLimit(4);
        fragment_list = new ArrayList<>();
        ViewpagerMusicFragment viewpagerLocalFragment = new ViewpagerMusicFragment();
        ViewpagerArtistFragment viewpagerArtistFragment = new ViewpagerArtistFragment();
        ViewpagerAlbumFragment viewpagerAlbumFragment = new ViewpagerAlbumFragment();
        ViewpagerFloderFragment viewpagerFloderFragment = new ViewpagerFloderFragment();
        fragment_list.add(viewpagerLocalFragment);
        fragment_list.add(viewpagerArtistFragment);
        fragment_list.add(viewpagerAlbumFragment);
        fragment_list.add(viewpagerFloderFragment);

        viewPager.setAdapter(new MyFragmentAdapter(getChildFragmentManager()));
        //tablayout 设置和viewpager联动会重新为tab  settext导致tab的text为空，所以要先设置联动，再去设置tab的text
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0 ; i < tab_name.length; i++){
            tabLayout.getTabAt(i).setText(tab_name[i]);
        }

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



    private  class MyFragmentAdapter extends FragmentPagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment_list.get(position);
        }

        @Override
        public int getCount() {
            return tab_name.length;
        }
    }

}
