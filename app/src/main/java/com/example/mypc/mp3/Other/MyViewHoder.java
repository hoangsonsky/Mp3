package com.example.mypc.mp3.Other;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mypc.mp3.R;

/**
 * Created by MyPC on 09/10/2016.
 */
public class MyViewHoder extends RecyclerView.ViewHolder {
    public Spinner spinner;
    public TextView tv_name;
    public TextView tv_artist;
    public ImageView icon;
    public MyViewHoder(View itemView) {
        super(itemView);
        spinner = (Spinner) itemView.findViewById(R.id.spinner);
        tv_name = (TextView) itemView.findViewById(R.id.tvNameSong);
        tv_artist = (TextView) itemView.findViewById(R.id.tvArtist);
        icon = (ImageView) itemView.findViewById(R.id.imgIcon);
    }

}
