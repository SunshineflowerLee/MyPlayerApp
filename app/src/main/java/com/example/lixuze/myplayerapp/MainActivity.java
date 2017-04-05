package com.example.lixuze.myplayerapp;

import android.animation.ArgbEvaluator;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.lixuze.myplayerapp.Tool.Util;
import com.example.lixuze.myplayerapp.fragment.IActivity;
import com.example.lixuze.myplayerapp.fragment.LocalMusicFragment;
import com.example.lixuze.myplayerapp.fragment.ViewpagerMainFragment1;
import com.example.lixuze.myplayerapp.fragment.ViewpagerMainFragment2;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IActivity,View.OnClickListener{
    private LocalMusicFragment myFragment;
    private View mainbar;
    private Toolbar toolbar;
    private FrameLayout lrc_view;
    private View play_bar;
    private NavigationView navView;
    private ViewPager viewPager;
    private ImageView llTitleDisco;
    private ImageView llTitleMusic;
    private ImageView llTitleFriends;

    private BottomSheetBehavior sheetBehavior;
    private List<Fragment> fragment_list;
    private int transparent_evaluate;
    private int Primary_evaluate;
    private int change_evaluate;
    private int barheight;

    private ILocalMusicPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBarView();
        initPlayBarView();
        initLrcView();
        initview();
        presenter = LocalMusicPresenter.getInstance();
        presenter.setContext(this);
        presenter.bindService();
    }

    @Override
    protected void onDestroy() {
        presenter.unBind();
        super.onDestroy();
    }

    private void initBarView() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mainbar = findViewById(R.id.mainbar_layout);
        setSupportActionBar(toolbar);
        initNavView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        llTitleDisco = (ImageView) findViewById(R.id.iv_title_disco);
        llTitleMusic = (ImageView) findViewById(R.id.iv_title_music);
        llTitleFriends = (ImageView) findViewById(R.id.iv_title_friends);
        llTitleDisco.setOnClickListener(this);
        llTitleMusic.setOnClickListener(this);
        llTitleFriends.setOnClickListener(this);
        llTitleDisco.setSelected(true);

        transparent_evaluate = getResources().getColor(R.color.colorPrimaryGray);
        change_evaluate = getResources().getColor(R.color.colorPrimaryGray);
        Primary_evaluate = getResources().getColor(R.color.colorPrimary);
        barheight = toolbar.getMinimumHeight()+Util.getsatsubarsize(this);
    }

    private void initNavView() {
        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
//        LinearLayout viewById1 = (LinearLayout) headerView.findViewById(R.id.ll_header_bg);
//        viewById1.setBackground();
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        //ImgLoadUtil.displayCircle(ivAvatar, R.drawable.ic_avatar);
        LinearLayout llNavHomepage = (LinearLayout) headerView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headerView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedback = (LinearLayout) headerView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headerView.findViewById(R.id.ll_nav_about);
        llNavHomepage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedback.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }



    private void initPlayBarView() {
        play_bar = (View) findViewById(R.id.play_bar);
        play_bar.setOnClickListener(this);

    }

    private void initLrcView() {
        lrc_view = (FrameLayout) findViewById(R.id.lrc_view);
        //获取BottomSheetBehavior
        sheetBehavior = BottomSheetBehavior.from(lrc_view);
        //设置折叠时的高度
        //sheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //监听BottomSheetBehavior 状态的变化
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //下滑的时候是否可以隐藏
        sheetBehavior.setHideable(true);
    }


    private void initview(){
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        viewPager.setOffscreenPageLimit(3);

        fragment_list = new ArrayList<>();
        ViewpagerMainFragment1 viewpagerMainFragment1 = new ViewpagerMainFragment1();
        ViewpagerMainFragment2 viewpagerMainFragment2 = new ViewpagerMainFragment2();
        ViewpagerMainFragment2 viewpagerMainFragment3 = new ViewpagerMainFragment2();
        fragment_list.add(viewpagerMainFragment2);
        fragment_list.add(viewpagerMainFragment1);
        fragment_list.add(viewpagerMainFragment3);

        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArgbEvaluator evaluator = new ArgbEvaluator();
                //position=0时是第一页到第二页的来回滑动监听，position=1时是第二页到第三页的来回滑动监听，以此类推
                if (position  == 0) {
                    int evaluate = (Integer) evaluator.evaluate(positionOffset, Primary_evaluate,change_evaluate);
                    mainbar.setBackgroundColor(evaluate);
                } else if(position  == 1){
                    int evaluate = (Integer) evaluator.evaluate(positionOffset, change_evaluate,Primary_evaluate);
                    mainbar.setBackgroundColor(evaluate);
                }else {
                    int evaluate = (Integer) evaluator.evaluate(positionOffset, Primary_evaluate,change_evaluate);
                    mainbar.setBackgroundColor(evaluate);
                }
                Log.d("position",position+"");

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        llTitleDisco.setSelected(true);
                        llTitleMusic.setSelected(false);
                        llTitleFriends.setSelected(false);
                        break;
                    case 1:
                        llTitleMusic.setSelected(true);
                        llTitleDisco.setSelected(false);
                        llTitleFriends.setSelected(false);
                        break;
                    case 2:
                        llTitleFriends.setSelected(true);
                        llTitleMusic.setSelected(false);
                        llTitleDisco.setSelected(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setToolbarColor(int scrollhigh, int scrollY) {
        Log.d("getCurrentItem:", viewPager.getCurrentItem() + "");
        if (viewPager.getCurrentItem() == 1) {
            scrollhigh = scrollhigh - barheight;
            if (scrollY <= scrollhigh) {
                float positionOffset = (float) scrollY / scrollhigh;
                Log.d("positionOffset", positionOffset + "");
                ArgbEvaluator evaluator = new ArgbEvaluator();
                change_evaluate = (Integer) evaluator.evaluate(positionOffset, transparent_evaluate, Primary_evaluate);
                mainbar.setBackgroundColor(change_evaluate);
            }
            Log.d("myscrollstate11", "  scrollhigh:" + scrollhigh + "  scrollY:" + scrollY);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onScroll(int scrollhigh, int scrollY) {
        setToolbarColor(scrollhigh,scrollY);
    }

    @Override
    public void addFragement(Fragment fromFragment , Fragment tofragment) {
        mainbar.setFitsSystemWindows(false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        transaction.hide(fromFragment);
        transaction.add(R.id.toolbar_layout,tofragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_bar:
                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
            case R.id.iv_title_disco:// 干货栏
                if (viewPager.getCurrentItem() != 0) {//不然cpu会有损耗
                    llTitleDisco.setSelected(true);
                    llTitleMusic.setSelected(false);
                    llTitleFriends.setSelected(false);
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_music:// 电影栏
                if (viewPager.getCurrentItem() != 1) {
                    llTitleMusic.setSelected(true);
                    llTitleFriends.setSelected(false);
                    llTitleDisco.setSelected(false);
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_friends:// 书籍栏
                if (viewPager.getCurrentItem() != 2) {
                    llTitleFriends.setSelected(true);
                    llTitleMusic.setSelected(false);
                    llTitleDisco.setSelected(false);
                    viewPager.setCurrentItem(2);
                }
                break;
        }
    }



    private  class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragment_list.get(position);
        }

        @Override
        public int getCount() {
            return fragment_list.size();
        }
    }


}
