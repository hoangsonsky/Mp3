package com.example.mypc.mp3.Other;

import android.app.Application;
import android.content.Context;

import com.example.mypc.mp3.Entity.EntitySong;

import java.util.ArrayList;

/**
 * Created by MyPC on 06/10/2016.
 */
public class SongApplication extends Application {

    ArrayList<EntitySong> mSongs = null;
    int mPosition = 0;
    boolean checkOnOrOff = false;
    String link;
    Context mContext;
    boolean checkNull = false;

    public boolean isCheckNull() {
        return checkNull;
    }

    public void setCheckNull(boolean checkNull) {
        this.checkNull = checkNull;
    }



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isCheckOnOrOff() {
        return checkOnOrOff;
    }

    public void setCheckOnOrOff(boolean checkOnOrOff) {
        this.checkOnOrOff = checkOnOrOff;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public ArrayList<EntitySong> getSongs() {
        return mSongs;
    }

    public void setSongs(ArrayList<EntitySong> songs) {
        this.mSongs = songs;
    }
}
