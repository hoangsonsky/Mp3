package com.example.mypc.mp3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.Constants;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.CustomView.ViewPlayMuSic1;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML1;
import com.example.mypc.mp3.R;

import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPlayMusicXML1 xml1 = (ViewPlayMusicXML1) findViewById(R.id.viewPlayMusic);
        getBundle();

        ViewPlayMuSic1 playMS1 = new ViewPlayMuSic1();
        playMS1.initView(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PlayMusicActivity.this, MyService.class);
                intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(intent);
//                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
        }).start();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ViewPagerPlayMSFragment fragmentMain = new ViewPagerPlayMSFragment();
        transaction.add(R.id.rltPlayMS, fragmentMain);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        String tag = bundle.getString(SongFragment.TAG);
        final ArrayList<EntitySong> mArrayList =
                bundle.getParcelableArrayList(SongFragment.PARCELABLE);
        final int position = bundle.getInt(SongFragment.POSITION);

        if (tag.equals(SongFragment.TAG)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent startIntent = new Intent(PlayMusicActivity.this,
                            MyService.class);
                    startIntent.setAction(Constants.ACTION.PLAY_ACTION);
                    startIntent.putExtra(SongFragment.POSITION,
                            mArrayList.get(position).getId());
                    startService(startIntent);
//                bindService(startIntent, mConnection, Context.BIND_AUTO_CREATE);

                }
            }).start();
        }

    }
}
