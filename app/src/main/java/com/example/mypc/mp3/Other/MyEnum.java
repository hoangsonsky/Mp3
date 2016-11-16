package com.example.mypc.mp3.Other;

/**
 * Created by MyPC on 05/10/2016.
 */
public enum MyEnum {
    SONG("song"),
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist");

    private String key;

    MyEnum(String key) {
        this.key = key;
    }
//
    @Override
    public String toString() {
        return key;
    }
}
