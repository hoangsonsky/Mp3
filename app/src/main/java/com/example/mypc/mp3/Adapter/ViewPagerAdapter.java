package com.example.mypc.mp3.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mypc.mp3.Fragment.AlbumFragment;
import com.example.mypc.mp3.Fragment.ArtistFragment;
import com.example.mypc.mp3.Fragment.PlayListFragment;
import com.example.mypc.mp3.Fragment.SongFragment;

/**
 * Created by Administrator on 25/04/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumTab;
    Bundle bundle;

    public ViewPagerAdapter(FragmentManager fm, int mNumTab,Bundle bundle) {
        super(fm);
        this.mNumTab = mNumTab;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PlayListFragment fragmentPlayList = new PlayListFragment();
                return fragmentPlayList;
            case 1:
                SongFragment fragmentSong = new SongFragment();
                fragmentSong.setArguments(this.bundle);
                return fragmentSong;
            case 2:
                AlbumFragment fragmentAlbum = new AlbumFragment();
                return fragmentAlbum;
            case 3:
                ArtistFragment fragmentArtist = new ArtistFragment();
                return fragmentArtist;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTab;
    }
}
