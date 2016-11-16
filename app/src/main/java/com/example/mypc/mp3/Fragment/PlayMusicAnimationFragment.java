package com.example.mypc.mp3.Fragment;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypc.mp3.Other.MyAnimation;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.R;


public class PlayMusicAnimationFragment extends Fragment {

//    SongApplication songApplication;
    TextView tvName, tvArtist;
    ImageView imgAnimation;
    MyAnimation myAnimation;
    ObjectAnimator objectAnimator;
    SongApplication songApplication;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra(MyService.DATA);
            boolean status = bundle.getBoolean(MyService.STATUS_MEDIA);

//            if (status==false){
//                myAnimation.pauseAnimation();
//            }
//            else
//                myAnimation.resumeAnimation();
        }
    };

    BroadcastReceiver mReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tvName.setText(
                    songApplication.getSongs().get(songApplication.getPosition()).getName());

            tvArtist.setText(
                    songApplication.getSongs().get(songApplication.getPosition()).getArtist());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songApplication = (SongApplication) getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_play_music_animation, container, false);
        tvName = (TextView) view.findViewById(R.id.tvNameSongPlay);
        tvArtist = (TextView) view.findViewById(R.id.tvArtistPlay);
        imgAnimation = (ImageView) view.findViewById(R.id.imgAnimation);

        myAnimation = new MyAnimation(objectAnimator, getContext());
        myAnimation.playAnimation(imgAnimation);

        tvName.setText(
                songApplication.getSongs().get(songApplication.getPosition()).getName());

        tvArtist.setText(
                songApplication.getSongs().get(songApplication.getPosition()).getArtist());

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.LOG_TAG);
                getActivity().registerReceiver(mReceiver, filter);
                IntentFilter filter1 = new IntentFilter(MyService.TAG);
                getActivity().registerReceiver(mReceiver1, filter1);
            }
        };
        handler.postDelayed(runnable,100);

        return view;
    }


}
