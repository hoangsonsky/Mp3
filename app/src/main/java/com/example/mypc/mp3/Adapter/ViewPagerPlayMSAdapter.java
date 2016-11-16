package com.example.mypc.mp3.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mypc.mp3.Fragment.BlankFragment;
import com.example.mypc.mp3.Fragment.LyricFragment;
import com.example.mypc.mp3.Fragment.PlayMusicAnimationFragment;

/**
 * Created by Administrator on 25/04/2016.
 */
public class ViewPagerPlayMSAdapter extends FragmentStatePagerAdapter {
    int mNumTab;

    public ViewPagerPlayMSAdapter(FragmentManager fm, int mNumTab) {
        super(fm);
        this.mNumTab = mNumTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BlankFragment blankFragment = new BlankFragment();
                return blankFragment;
            case 1:
                PlayMusicAnimationFragment fragmentSong
                        = new PlayMusicAnimationFragment();
//                fragmentSong.setArguments(this.fragmentBundle);
                return fragmentSong;
            case 2:
                LyricFragment lyricFragment = new LyricFragment();
                return lyricFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumTab;
    }
}
