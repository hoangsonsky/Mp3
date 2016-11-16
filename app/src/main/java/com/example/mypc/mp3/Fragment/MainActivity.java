package com.example.mypc.mp3.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.R;

public class MainActivity extends AppCompatActivity {

    ViewPlayMusicXML xml;
    SongApplication songApplication ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        songApplication = (SongApplication)getApplicationContext();
        xml = (ViewPlayMusicXML)findViewById(R.id.view1);
//        xml.setVisibility(View.GONE);
        if (songApplication.isCheckNull()==false){
            xml.setVisibility(View.GONE);
        }else {
            ViewPlayMusic playMusic = new ViewPlayMusic();
            playMusic.initCustomPlay(this);
        }

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.LOG_TAG);
                registerReceiver(mReceiverPosition, filter);
            }
        };
        handler.postDelayed(runnable, 100);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListFragment fragmentMain = new ListFragment();
        transaction.add(R.id.rlt, fragmentMain);
        transaction.commit();
    }

}
