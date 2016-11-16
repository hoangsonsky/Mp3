package com.example.mypc.mp3.Entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

/**
 * Created by Administrator on 26/04/2016.
 */
public class EntitySong implements Parcelable {
    private String mId;

    protected EntitySong(Parcel in) {
        mId = in.readString();
        mData = in.readString();
        mName = in.readString();
        mArtist = in.readString();
        mIcon = in.readInt();
    }

    public EntitySong() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mData);
        dest.writeString(mName);
        dest.writeString(mArtist);
        dest.writeInt(mIcon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EntitySong> CREATOR = new Creator<EntitySong>() {
        @Override
        public EntitySong createFromParcel(Parcel in) {
            return new EntitySong(in);
        }

        @Override
        public EntitySong[] newArray(int size) {
            return new EntitySong[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    private String mData;

    public String getData() {
        return mData;
    }

    public void setData(String mData) {
        this.mData = mData;
    }

    private String mName;
    private String mArtist;
    private ArrayAdapter<String> adapter;
    private int mIcon;

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String mArtist) {
        this.mArtist = mArtist;
    }
}
