package com.example.haircal;

import java.util.ArrayList;

public class AlbumVO {

    private String thumb;

    private String artist;

    private String date;

    private String type;

    private String title;

    private ArrayList<String> song;

    private String introduce;



    public String getThumb() {

        return thumb;

    }



    public void setThumb(String thumb) {

        this.thumb = thumb;

    }



    public String getArtist() {

        return artist;

    }



    public void setArtist(String artist) {

        this.artist = artist;

    }



    public String getDate() {

        return date;

    }



    public void setDate(String date) {

        this.date = date;

    }



    public String getType() {

        return type;

    }



    public void setType(String type) {

        this.type = type;

    }



    public String getTitle() {

        return title;

    }



    public void setTitle(String title) {

        this.title = title;

    }



    public ArrayList<String> getSong() {

        return song;

    }



    public void setSong(ArrayList<String> song) {

        this.song = song;

    }



    public String getIntroduce() {

        return introduce;

    }



    public void setIntroduce(String introduce) {

        this.introduce = introduce;

    }



    @Override

    public String toString() {

        return "AlbumVO{" +

                "thumb='" + thumb + '\'' +

                ", artist='" + artist + '\'' +

                ", date='" + date + '\'' +

                ", type='" + type + '\'' +

                ", title='" + title + '\'' +

                ", song=" + song +

                ", introduce='" + introduce + '\'' +

                '}';

    }

}