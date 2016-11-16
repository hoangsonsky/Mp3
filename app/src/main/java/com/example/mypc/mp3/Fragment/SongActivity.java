package com.example.mypc.mp3.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.R;

public class SongActivity extends AppCompatActivity {

    SongApplication songApplication;
    ViewPlayMusicXML xml;

    BroadcastReceiver mReceiverPosition = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = songApplication.isCheckNull();
            if (status==false){
                xml.setVisibility(View.GONE);
            }else
                xml.setVisibility(View.VISIBLE);
        }
    };
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songApplication= (SongApplication)getApplicationContext();

        xml = (ViewPlayMusicXML)findViewById(R.id.viewSong);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.LOG_TAG);
                registerReceiver(mReceiverPosition, filter);
            }
        };
        handler.postDelayed(runnable, 100);

//        xml.setVisibility(View.GONE);

        if (songApplication.isCheckNull()==false){
            xml.setVisibility(View.GONE);
        }else {
            ViewPlayMusic playMusic = new ViewPlayMusic();
            playMusic.initCustomPlay(this);
        }
//        ViewPlayMusic playMusic = new ViewPlayMusic();
//        playMusic.initCustomPlay(this);

        getBundle();

    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        boolean check = bundle.getBoolean(SongFragment.CHECK);
        if (check){
            String name = bundle.getString(SongFragment.NAME);
            String tag  = bundle.getString(ViewPagerFragment.TAG);

            Bundle bundle1 = new Bundle();
            bundle1.putString(SongFragment.NAME,name);
            bundle1.putString(ViewPagerFragment.TAG, tag);

            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Song123Fragment fragmentMain = new Song123Fragment();
            fragmentMain.setArguments(bundle1);
            transaction.add(R.id.rltSong, fragmentMain);
            transaction.commit();
        }
        else {
            String key = bundle.getString(SongFragment.TEXT_SEARCH);
            Bundle bundle1 = new Bundle();
            bundle1.putString(SongFragment.TEXT_SEARCH, key);

            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            OnlineSongFragment fragmentMain = new OnlineSongFragment();
            fragmentMain.setArguments(bundle1);
            transaction.add(R.id.rltSong, fragmentMain);
            transaction.commit();
        }
    }

}
