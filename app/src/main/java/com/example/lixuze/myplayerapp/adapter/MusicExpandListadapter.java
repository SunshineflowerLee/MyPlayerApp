package com.example.lixuze.myplayerapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.entites.Music_info;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/1/9.
 */
public class MusicExpandListadapter extends BaseExpandableListAdapter {
    private final String TAG = "MusicExpandListadapter";
    private LayoutInflater inflater;
    private ExpandableListView expandableListView;
    private List<Music_info> list;
    private String selection;
    private int type;
    private Context context;
    private Intent intent;
    private  Music_info music_info;
    private ILocalMusicPresenter presenter;
    private boolean checked=false;


    public MusicExpandListadapter(Context context, ExpandableListView expandableListView, int type, String selection) {
        inflater = LayoutInflater.from(context);
        presenter = LocalMusicPresenter.getInstance();
        this.expandableListView = expandableListView;
        this.context = context;
        this.selection = selection;
        this.type = type;

        //根据点击进来的方式
        getListByType(type,selection);
    }

    public List<Music_info> getListByType(int type,String selection) {
        list = presenter.getMusicListBytype(type,selection);
        if (list == null){
            list = new ArrayList<>();
        }
        Log.d(TAG,list.size()+"");
        return list;
    }

    public List<Music_info> getMusicList(){
        return list;
    };
    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        music_info = list.get(groupPosition);
        GroupViewHolder groupviewHolder;
        if (convertView == null) {
            groupviewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.local_expand_list_group, null);
            groupviewHolder.isexpand_img = (ImageView) convertView.findViewById(R.id.isexpand_img);
            groupviewHolder.order = (TextView) convertView.findViewById(R.id.order);
            groupviewHolder.Song_Name = (TextView) convertView.findViewById(R.id.Song_Name);
            groupviewHolder.Singer_Name = (TextView) convertView.findViewById(R.id.Singer_Name);
            convertView.setTag(groupviewHolder);
        } else {
            groupviewHolder = (GroupViewHolder) convertView.getTag();
        }

        if (isExpanded) {
            groupviewHolder.isexpand_img.setImageResource(R.mipmap.child_close);
        } else {
            groupviewHolder.isexpand_img.setImageResource(R.mipmap.child_expand);
        }
        //自定义expandablelistview下拉方式，不再为原始的点击item下拉，而是点击一个图标下拉
        groupviewHolder.isexpand_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    expandableListView.expandGroup(groupPosition, true);//true代表使用子列表默认动画
                }
            }
        });
        groupviewHolder.order.setText(groupPosition + 1 + "");
        groupviewHolder.Song_Name.setText(music_info.getMusicname());
        groupviewHolder.Singer_Name.setText(music_info.getArtist());
        //自定义点击事件
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                presenter.startMusic(list,groupPosition);
                return true;
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.local_expand_list_child, null);
            childViewHolder.love = (LinearLayout) convertView.findViewById(R.id.love);
            childViewHolder.delete = (LinearLayout) convertView.findViewById(R.id.delete);
            childViewHolder.love_img = (ImageView) convertView.findViewById(R.id.love_img);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
       /*  music_info = list.get(groupPosition);
        final MusicInfoDao musicInfoDao = new MusicInfoDao(context);
        if (music_info.getFavorite() == 0) {
            childViewHolder.love_img.setImageResource(R.mipmap.love);
        } else {
            childViewHolder.love_img.setImageResource(R.mipmap.love_in);
        }
        childViewHolder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=switchType(type);
                music_info = list.get(groupPosition);
                FavoriteInfoDao favoriteInfoDao = new FavoriteInfoDao(context);
                if (music_info.getFavorite() == 0) {
                    childViewHolder.love_img.setImageResource(R.mipmap.love_in);
                    musicInfoDao.updataLove(music_info.getUrl(), 1);
                    favoriteInfoDao.saveFavoriteInfo(music_info);
                    //                   favoriteInfoDao.updataLove(music_info_in.getUrl(), 1);
                } else {
                    childViewHolder.love_img.setImageResource(R.mipmap.love);
                    musicInfoDao.updataLove(music_info.getUrl(), 0);
                    favoriteInfoDao.deleteFavorite(music_info.getUrl());
                }

                if(type==START_FROM_FAVORITE){
                *//* 注意必须重新加载list，因为刷新list的方法必须要先得到最新的list，再判断最新的list
                    跟之前比是不是已经变了，才会执行刷新*//*
                   list=switchType(type);
                   flash_list();
                }
                Intent intent_love = new Intent("intent_love");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent_love);
            }
        });
        childViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music_info = list.get(groupPosition);
                LinearLayout alertdialog_delete= (LinearLayout) inflater.
                        inflate(R.layout.alertdialog_delete, null);
                TextView alertdialog_text= (TextView) alertdialog_delete.findViewById(R.id.alertdialog_text);
                final CheckBox alertdialog_checkbox= (CheckBox) alertdialog_delete.findViewById(R.id.alertdialog_checkbox);
                alertdialog_text.setText("歌曲："+music_info.getMusicname()+"？");
                alertdialog_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      checked=isChecked;
                    }
                });
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("确认删除");
                builder.setView(alertdialog_delete);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (music_info.getFavorite() == 1) {
                            FavoriteInfoDao favoriteInfoDao = new FavoriteInfoDao(context);
                            favoriteInfoDao.deleteFavorite(music_info.getUrl());
                        }
                        Music_util.delete_music(context, checked, music_info.getUrl());
                        list=switchType(type);
                        flash_list();
                        Intent intent_delete = new Intent("intent_delete");
                        context.sendOrderedBroadcast(intent_delete,null);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });*/

        return convertView;
    }

//该函数用来刷新listview
    private void flash_list(){
        this.notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        ImageView isexpand_img;
        TextView order;
        TextView Song_Name;
        TextView Singer_Name;
    }

    class ChildViewHolder {
        LinearLayout love;
        LinearLayout delete;
        ImageView love_img;
    }
}

