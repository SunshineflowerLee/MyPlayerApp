package com.example.lixuze.myplayerapp.entites;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by acer on 2016/1/20.
 */
public class Music_info implements Parcelable{
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

/*    protected Music_info(Parcel in) {
        songid = in.readInt();
        albumid = in.readInt();
        duration = in.readInt();
        musicname = in.readString();
        artist = in.readString();
        url = in.readString();
        folder = in.readString();
        favorite = in.readInt();
        musicnamekey = in.readString();
        artistkey = in.readString();
    }*/

    public static final Creator CREATOR = new Creator() {
        @Override
        public Music_info createFromParcel(Parcel in) {
            Music_info music_info=new Music_info();
            music_info.songid = in.readInt();
            music_info.albumid = in.readInt();
            music_info.duration = in.readInt();
            music_info.musicname = in.readString();
            music_info.artist = in.readString();
            music_info.url = in.readString();
            music_info.folder = in.readString();
            music_info.favorite = in.readInt();
            music_info.musicnamekey = in.readString();
            music_info.artistkey = in.readString();
            music_info.musictab = in.readInt();
            return music_info;
        }

        @Override
        public Music_info[] newArray(int size) {
            return new Music_info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(songid);
        dest.writeInt(albumid);
        dest.writeInt(duration);
        dest.writeString(musicname);
        dest.writeString(artist);
        dest.writeString(url);
        dest.writeString(folder);
        dest.writeInt(favorite);
        dest.writeString(musicnamekey);
        dest.writeString(artistkey);
        dest.writeInt(musictab);
    }

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
