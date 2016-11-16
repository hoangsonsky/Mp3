package com.example.mypc.mp3.CustomView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mypc.mp3.Other.Constants;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.R;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.Fragment.SongFragment;

import java.util.Random;

/**
 * Created by MyPC on 14/07/2016.
 */
public class ViewPlayMuSic1 extends Fragment {

    ImageView imgPauseOrPlay;
    ImageView imgPrevSong;
    ImageView imgNextSong;
    ImageView imgRandom;
    ImageView imgLoop;
    TextView tvTimeCurrent, tvTimefinal;
    SeekBar sb;
    long mDuration = 0;


    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra(MyService.DATA);
            int progress = bundle.getInt(MyService.PROGRESS_SB);
            String currentTime = bundle.getString(MyService.CURRENT_TIME);
            String fialTime = bundle.getString(MyService.FINAL_TIME);
            mDuration = bundle.getLong(MyService.DURATION_SONG);
            updateSeekbarAndTine(progress, currentTime, fialTime);
            boolean status = bundle.getBoolean(MyService.STATUS_MEDIA);
            if (status == false) {
                imgPauseOrPlay.setImageResource(R.drawable.play);
            } else
                imgPauseOrPlay.setImageResource(R.drawable.pause);
            boolean checkLoop = bundle.getBoolean(MyService.CHECK_LOOP);
            if (checkLoop == false) {
                imgLoop.setImageResource(R.drawable.knob_loop_off);
            } else
                imgLoop.setImageResource(R.drawable.loop);
        }
    };


    private void updateSeekbarAndTine(int progress,
                                      String currentTime,
                                      String finalTime) {
        sb.setProgress(progress);
        tvTimeCurrent.setText(currentTime);
        tvTimefinal.setText(finalTime);
    }

    public void initView(final Activity activity) {
        final SongApplication songApplication = (SongApplication) activity.getApplicationContext();
        imgPauseOrPlay = (ImageView) activity.findViewById(R.id.imgPauseOrPlay1);
        imgPrevSong = (ImageView) activity.findViewById(R.id.imgBackSong1);
        imgLoop = (ImageView) activity.findViewById(R.id.imgLoop1);
        imgRandom = (ImageView) activity.findViewById(R.id.imgRandom1);
        imgNextSong = (ImageView) activity.findViewById(R.id.imgNextSong1);
        tvTimeCurrent = (TextView) activity.findViewById(R.id.tv_timeCurrent1);
        tvTimefinal = (TextView) activity.findViewById(R.id.tv_timefinal1);
        sb = (SeekBar) activity.findViewById(R.id.sb_music1);
        sb.setProgress(0);
        sb.setMax(100);

        Intent startIntent = new Intent(activity,
                MyService.class);
        activity.startService(startIntent);

        imgPauseOrPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(activity, MyService.class);
                startIntent.setAction(Constants.ACTION.PAUSE);
                activity.startService(startIntent);
            }
        });
        imgNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.NEXT_ACTION);
                activity.startService(intent);

            }
        });
        imgPrevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.PREV_ACTION);
                activity.startService(intent);

            }
        });

        imgLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.LOOP);
                activity.startService(intent);
            }
        });

        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rn = new Random();
                int range = songApplication.getSongs().size();
                int randomNum = rn.nextInt(range);
                songApplication.setPosition(randomNum);

                Intent startIntent = new Intent(activity,
                        MyService.class);
                startIntent.setAction(Constants.ACTION.PLAY_ACTION);
                startIntent.putExtra(SongFragment.POSITION,
                        songApplication.getSongs().
                                get(randomNum).getId());
                activity.startService(startIntent);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.LOG_TAG);
                activity.registerReceiver(mReceiver, filter);
            }
        }).start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Intent intent = new Intent(activity, MyService.class);
                intent.setAction(Constants.ACTION.UPDATE_SEEKBAR);
                Bundle bundle = new Bundle();
                bundle.putInt(ViewPlayMusic.NEXT_SONG,
                        (int) ((seekBar.getProgress() * mDuration) / seekBar.getMax()));
                intent.putExtras(bundle);
                activity.startService(intent);
            }
        });
    }

}
