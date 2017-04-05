package com.example.lixuze.myplayerapp.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lixuze.myplayerapp.R;
import com.example.lixuze.myplayerapp.constant.Constants;
import com.example.lixuze.myplayerapp.entites.Album_info;
import com.example.lixuze.myplayerapp.entites.Artist_info;
import com.example.lixuze.myplayerapp.entites.Folder_info;
import com.example.lixuze.myplayerapp.persenter.IPersenter.ILocalMusicPresenter;
import com.example.lixuze.myplayerapp.persenter.LocalMusicPresenter;

import java.util.ArrayList;
import java.util.List;


public class LocalRecyclerAdapter extends RecyclerView.Adapter<LocalRecyclerAdapter.ViewHolder>{
    private List list;
    private int type;
    private ILocalMusicPresenter presenter;
    public OnItemClickListener onItemClickListener;

    public LocalRecyclerAdapter(int type) {
        this.type = type;
        presenter = LocalMusicPresenter.getInstance();
        getListByType(type);
    }

    public List getListByType(int type){
        switch (type){
            case Constants.START_FROM_ARTIST:
                list =presenter.getArtistList();
                break;
            case Constants.START_FROM_ALBUM:
                list = presenter.getAlbumList();
                break;
            case Constants.START_FROM_FOLDER:
                list = presenter.getFoloderList();
                break;
        }
        if (list == null){
            list = new ArrayList<>();
        }
        return list;
    }
    @Override
    public LocalRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_recycler_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(LocalRecyclerAdapter.ViewHolder holder, int position) {
        switch (type){
            case Constants.START_FROM_ARTIST:
                holder.itemmaintitle.setText(((Artist_info)(list.get(position))).getArtist_name());
                holder.itemsubtitle.setText(((Artist_info)(list.get(position))).getNumber_of_tracks());
                break;
            case Constants.START_FROM_ALBUM:
                holder.itemmaintitle.setText(((Album_info)(list.get(position))).getAlbum_name());
                holder.itemsubtitle.setText(((Album_info)(list.get(position))).getNumber_of_songs());
                break;
            case Constants.START_FROM_FOLDER:
                holder.itemmaintitle.setText(((Folder_info)(list.get(position))).getFolder_name());
                holder.itemsubtitle.setText(((Folder_info)(list.get(position))).getFolder_path());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout itemlayout;
        public ImageView itemimage;
        public TextView itemmaintitle;
        public TextView itemsubtitle;
        public ViewHolder(View itemView) {
            super(itemView);
            itemlayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            //itemimage = (ImageView) itemimage.findViewById(R.id.item_image);
            itemmaintitle = (TextView) itemView.findViewById(R.id.item_maintitle);
            itemsubtitle = (TextView) itemView.findViewById(R.id.item_subtitle);
            itemlayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(itemView,getAdapterPosition());
            }

        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View item ,int position);
    }
}
