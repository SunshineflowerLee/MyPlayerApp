package com.example.lixuze.myplayerapp.entites;

/**
 * Created by acer on 2016/1/25.
 */
public class Album_info {
    //专辑名称
    private String album_name;
    //专辑在数据库中的id
    private int album_id = -1;
    //专辑的歌曲数目
    private int number_of_songs = 0;
    //专辑封面图片路径
    private String album_art;

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }
}
