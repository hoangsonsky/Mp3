package com.example.mypc.mp3.CustomView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mypc.mp3.Other.Constants;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Fragment.PlayMusicActivity;
import com.example.mypc.mp3.R;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.Fragment.SongFragment;


/**
 * Created by MyPC on 5/10/2016.
 */
public class ViewPlayMusic extends Fragment {

    public static final String NEXT_SONG = "next_song";
    public static final String BACK_SONG = "back_song";
    public static final String TAG = "ViewPlayMusic";
    ImageView imgNextCustomPlay;
    ImageView imgPrevCustomPlay;
    ImageView imgPlayCustomPlay;
    RelativeLayout relativeLayout;
    TextView tvNameCustomPlay, tvArtistCustomPlay;
    SongApplication songApplication;


    BroadcastReceiver mReceiverPosition = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tvNameCustomPlay.setText(
                    songApplication.getSongs().get(songApplication.getPosition()).getName());

            tvArtistCustomPlay.setText(
                    songApplication.getSongs().get(songApplication.getPosition()).getArtist());
        }
    };

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra(MyService.DATA);
            boolean status = bundle.getBoolean(MyService.STATUS_MEDIA);

            if (status==false){
                imgPlayCustomPlay.setImageResource(R.drawable.play);
            }else
                imgPlayCustomPlay.setImageResource(R.drawable.pause);
//            tvNameCustomPlay.setText(
//                    songApplication.getSongs().get(songApplication.getPosition()).getName());
//
//            tvArtistCustomPlay.setText(
//                    songApplication.getSongs().get(songApplication.getPosition()).getArtist());
        }
    };

    public void initCustomPlay(final Activity activity) {
        songApplication = (SongApplication) activity.getApplicationContext();
        imgPrevCustomPlay = (ImageView) activity.findViewById(R.id.img_backCustomPlay);
        imgPlayCustomPlay = (ImageView) activity.findViewById(R.id.img_playCustomPlay);
        imgNextCustomPlay = (ImageView) activity.findViewById(R.id.img_nextCustomPlay);
        tvNameCustomPlay = (TextView) activity.findViewById(R.id.tv_nameCustomPlay);
        relativeLayout = (RelativeLayout) activity.findViewById(R.id.rlt_custom);
        tvArtistCustomPlay = (TextView) activity.findViewById(R.id.tv_artistCustomPlay);

        tvNameCustomPlay.setText(
                songApplication.getSongs().get(songApplication.getPosition()).getName());

        tvArtistCustomPlay.setText(
                songApplication.getSongs().get(songApplication.getPosition()).getArtist());

        new Thread(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.TAG);
                activity.registerReceiver(mReceiverPosition, filter);
            }
        }).start();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.LOG_TAG);
                activity.registerReceiver(mReceiver, filter);
            }
        };
        handler.postDelayed(runnable,100);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SongFragment.TAG,TAG);
                bundle.putParcelableArrayList(SongFragment.PARCELABLE,
                        songApplication.getSongs());
                bundle.putInt(SongFragment.POSITION,songApplication.getPosition());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        imgPlayCustomPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(activity, MyService.class);
                startIntent.setAction(Constants.ACTION.PAUSE);
                activity.startService(startIntent);
            }
        });
        imgNextCustomPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.NEXT_ACTION);
                activity.startService(intent);
            }
        });
        imgPrevCustomPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.PREV_ACTION);
                activity.startService(intent);
            }
        });
    }

    public void setTextForTV(String name, String artirt) {
        tvNameCustomPlay.setText(name);
        tvArtistCustomPlay.setText(artirt);
    }
}
