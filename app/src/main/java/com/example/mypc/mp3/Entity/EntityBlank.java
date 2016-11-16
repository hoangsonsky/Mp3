package com.example.mypc.mp3.Entity;

/**
 * Created by MyPC on 15/07/2016.
 */
public class EntityBlank {
    private String mID;
    private String mName;
    private String mSinger;
    int mIcon;

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSinger() {
        return mSinger;
    }

    public void setSinger(String mSinger) {
        this.mSinger = mSinger;
    }
}
