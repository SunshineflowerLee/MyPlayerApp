package com.example.lixuze.myplayerapp.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.adapter.entites.SongList;


import java.util.List;

public class QuickAdapter extends BaseQuickAdapter<SongList, BaseViewHolder> {
    public QuickAdapter(List<SongList> list) {
        super( R.layout.viewpager_main1_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongList item) {
        //helper.setText(R.id.music_player, item.getSongListName());
    }


}
