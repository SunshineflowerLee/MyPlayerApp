package com.example.lixuze.myplayerapp.entites;

/**
 * Created by acer on 2016/1/31.
 */
public class Favorite_info {
    private  int songid;//歌曲id
    private  int albumid;//歌曲专辑
    private  int duration;//歌曲时长
    private  String musicname;//歌曲名称
    private  String artist;//歌曲作者
    private  String url;//歌曲路径
    private  String folder;//歌曲所在文件夹
    private  int favorite;
    private  String musicnamekey;
    private  String artistkey;
    private  int musictab;

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int album) {
        this.albumid = album;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getMusicnamekey() {
        return musicnamekey;
    }

    public void setMusicnamekey(String musicnamekey) {
        this.musicnamekey = musicnamekey;
    }

    public String getArtistkey() {
        return artistkey;
    }

    public void setArtistkey(String artistkey) {
        this.artistkey = artistkey;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getMusictab() {
        return musictab;
    }

    public void setMusictab(int musictab) {
        this.musictab = musictab;
    }
}
